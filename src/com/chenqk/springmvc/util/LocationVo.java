package com.chenqk.springmvc.util;

import java.io.Serializable;
/**
 * 实时上报的采集数据VO
 */
public class LocationVo implements Serializable{
	private static final long serialVersionUID = -1104921622958421405L;
	private String apMac;// apMac 暂时用BSSID代替
	private String staMac;//终端MAC地址
	private double RSSI;// RSSI
	private int TOA;// TOA
	private String collTime;// 采集时间
	private int rate;
	private String mode;
	private String ESSID;
	private int channel;
	private int stationType;

	public LocationVo(){}
	
	public LocationVo(String apMac, String staMac, double rssi, int toa,
			String collTime, int rate, String mode, String essid) {
		super();
		this.apMac = apMac;
		this.staMac = staMac;
		this.RSSI = rssi;
		this.TOA = toa;
		this.collTime = collTime;
		this.rate = rate;
		this.mode = mode;
		this.ESSID = essid;
	}

	public String getApMac() {
		return apMac;
	}

	public void setApMac(String apMac) {
		this.apMac = apMac;
	}

	public double getRSSI() {
		return RSSI;
	}

	public void setRSSI(double rssi) {
		RSSI = rssi;
	}

	public int getTOA() {
		return TOA;
	}

	public void setTOA(int toa) {
		TOA = toa;
	}

	public String getCollTime() {
		return collTime;
	}

	public void setCollTime(String collTime) {
		this.collTime = collTime;
	}
	
	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getESSID() {
		return ESSID;
	}

	public void setESSID(String essid) {
		ESSID = essid;
	}
	
	public String getStaMac() {
		return staMac;
	}

	public void setStaMac(String staMac) {
		this.staMac = staMac;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}

	public int getStationType() {
		return stationType;
	}

	public void setStationType(int stationType) {
		this.stationType = stationType;
	}
}
