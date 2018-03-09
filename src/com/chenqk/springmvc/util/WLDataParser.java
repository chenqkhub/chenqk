package com.chenqk.springmvc.util;

import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 定位数据解析类，提供定位数据解析方法
 * @author RenLiang
 *
 */
public class WLDataParser extends AbstractWLDataParserUtil{

	private int _real_length_perStation;

	public List<LocationVo> parser(byte[] data ,String mac){
		
		List<LocationVo> stationList = new ArrayList<LocationVo>();
		try{
			// 判断是否是定位报文
			int packageType = data[_position_packageType];
//			System.out.println("_position_packageType：" + _position_packageType);
			if(data.length == 1){
				return null;
			}
			if (packageType != 0) {
				return null;
			}
			// 报文协议版本
//			int version = data[_position_version];
			int version = data[_position_staNum-1]==0x01 ? 0 : 1;
//			log.info("WIFILocation_Package version：" + version);
			// AP MAC地址
			String apMac = "";
			byte[] apMacArray = new byte[_position_apMacLength];
			for (int i = _position_apMac; i < _position_apMac
					+ _position_apMacLength; i++) {
				apMacArray[i - _position_apMac] = data[i];
			}
			apMac = ByteUtil.bytesToHexString(apMacArray);
//			System.out.println("apMac：" + apMac);
			// station数量
			int staNum = 0;
			if(version==0){
				staNum = data[_position_staNum];
			} else {
				staNum = ByteUtil.bytesToInt(data, _position_staNum, _position_staNumLengthV2);
			}
//			System.out.println("staNum：" + staNum);
			// 消息长度，版本1应该是staNum*34
			int messageLength = 0;
			if(version==0){
				messageLength = ByteUtil.bytesToInt(data, _position_messageLength,
					_length_messageLength);
			} else {
				messageLength = ByteUtil.bytesToInt(data, _position_messageLengthV2,
						_length_messageLengthV2);
			}
//			System.out.println("messageLength：" + messageLength);
			// 实际每个Station的消息长度
			if(staNum == 0){
				return null ;
			}
			int eachLength = messageLength % staNum;
			if (eachLength != 0) {
				return null;
			}
			_real_length_perStation = messageLength / staNum;

			// 解析数据包中的每个Station数据
			
			int _position_messageStart_temp = 0;
			if(version==0){
				_position_messageStart_temp = _position_messageStart;
			} else {
				_position_messageStart_temp = _position_messageStartV2;
			}
			
			if(_real_length_perStation == 0){
				return null;
			}
			for (int i = 0, start = _position_messageStart_temp; i < staNum; i++, start += _real_length_perStation) {
				LocationVo sd = new LocationVo();
				sd.setApMac(apMac);
				sd.setCollTime(DateUtil.dateToAllCode(new Date()));
				// 解析一个station的信息
				parseOneStation(data, start, _real_length_perStation, sd);
				
				//sd.setCollTime(String.valueOf(new Date().getTime()));
				if(sd.getStaMac()!=null && !sd.getStaMac().equals("")&& sd.getRSSI() > -90){
					
					if(mac.equals(sd.getStaMac())){// 匹配该设备Mac是否是要监听的Mac设备
						//stationList.add(sd);// 将符合的Mac设备存入集合中
						DBUtil.getDBConnect(sd);
					}
				}
			}
			
//			System.out.println("Ap：" + apMac + "--简析用户报文"+stationList.size());
			//更新AP监控状态
			WifiMonitoringApCache.getInstance().putData(apMac);
			
//			if(locater.showPositionSwitch()){
//				//定位开启时放同步器
//				WifiLocationDataCaches.putData(stationList);
//				//保存原始定位数据
//				if(WifiOriginalDataCache.getSaveStatus())
//					WifiOriginalDataCache.addSourceData(stationList);
//			}else{
//				//保存原始指纹数据
//				WifiOriginalDataCache.addSourceData(stationList);
//			}
			return stationList;
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return stationList;
	}
	
	public void parser(DatagramPacket packet) {

		System.out.println(packet.getAddress().getHostAddress());
		byte[] data = packet.getData();
		this.parser(data,"");		
	}
	
	/**
	 * 解析单个Station
	 * @param data 报文内容。报文整体内容
	 * @param start 解析的起始位置
	 * @param unitLength 每个Station的报文长度
	 * @param sd 返回的sd对象
	 */
	public void parseOneStation(byte[] data, int start, int unitLength, LocationVo sd){
		int dataSize = data.length;
//		if(unitLength == 0){
//			log.info("WIFILocation_Parse Data Package Error. The Length Of each Station is 0!");
//			return ;
//		}
		if(dataSize < start + unitLength){
			return ;
		}
		
		//预定义station属性
//		int _station_Type = 0;
		String _station_Mac = "";
		byte _station_RSSI = 0;
//		String _station_TOA = "0";
//		int _station_interval = 0;
		byte _station_channel = 0;
		
		//开始解析
		int endPostion = start + unitLength;
		int nowPostion = start;
		while(nowPostion < endPostion){
				
			int type = data[nowPostion];
			int length = data[nowPostion+1];
//			if(type == _type_sta_type){
//				_station_Type = data[nowPostion+2];
//			} else 
			if(type == _type_sta_mac){
				byte[] staMacArray = new byte[length];
				for(int i = 0; i < length; i++){
					staMacArray[i] = data[nowPostion + 2 + i];
				}
				_station_Mac = ByteUtil.bytesToHexString(staMacArray);
			} else if(type == _type_sta_rssi){
				_station_RSSI = data[nowPostion+2];
			} 
//			else if(type == _type_sta_toa){
//				/**
//				 * v1版本中暂不处理
//				 */
////				_station_TOA = data[start+2+length];
//				
//			} else if(type == _type_sta_interval){
//				/**
//				 * v1版本中暂不处理
//				 */
////				_station_interval = data[start+2+length];
//				
//			} 
			else if(type == _type_sta_channel){
				/**
				 * v1版本中只处理2.4G信道，5G信道可能超过byte范围，需要处理
				 */
				_station_channel = data[nowPostion+2];
			}
			nowPostion += (2 + length); 
		}
		sd.setChannel(_station_channel & 0xff );
		sd.setRSSI(_station_RSSI - 95);
		sd.setTOA(0);
		sd.setStaMac(_station_Mac);
		//sd.setStationType(OnlineStationCache.getInstance().isStationOnline(sd.getStaMac()) ? 1 : 0);
		
//		if(sd.getStaMac().equals("e4:32:cb:a7:1f:42")){
//			OnlineStationCache.getInstance().print();
//			log.info("***************收到 e4:32:cb:a7:1f:42的报文，stationType是"+sd.getStationType()+"*****************************");
//			
//		}
	}
	
	/**
	 * 判断报文是否合法
	 * 比较报文体长度和StaNum个数的关系是否正确
	 * @param version 报文版本
	 * @param staNum 报文体包含的station个数
	 * @param messageLength 报文体长度
	 * @return 如果报文体长度与staNum长度相等，那么返回true，否则返回false
	 */
    public boolean validate(int version, int staNum, int messageLength){
		if(version == 0){
			if(staNum * _length_perStation == messageLength){
				return true;
			}
		}
		return false;
	}

}
