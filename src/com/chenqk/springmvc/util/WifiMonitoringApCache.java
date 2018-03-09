package com.chenqk.springmvc.util;

import java.util.HashMap;
import java.util.Map;


/**
 * Ap扫描状态监控缓存
 * @author Earl
 */
public class WifiMonitoringApCache {
	private static HashMap<String,String> apMonitoringStatus = new HashMap<String,String>();
	
	/**
	 * 单例模式
	 * @author dell
	 *
	 */
	private static class Singleton {
		public final static WifiMonitoringApCache instance = new WifiMonitoringApCache();
	}
	
	public static WifiMonitoringApCache getInstance() {
		return Singleton.instance;
	} 
	
	public void init(){
		try{
			apMonitoringStatus = new HashMap<String,String>();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void putData(String apMac){
		try{
			apMonitoringStatus.put(apMac, DateUtil.getNowTime());
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public Map<String, String> getDatas(){
		try{
			return (Map<String, String>)apMonitoringStatus.clone();
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
