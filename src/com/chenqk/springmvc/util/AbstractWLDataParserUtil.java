package com.chenqk.springmvc.util;

/**
 * 定位数据解析公共虚类，定义报文信息
 * 
 * @author RenLiang
 * 
 */
public abstract class AbstractWLDataParserUtil {

	// 报文头位置数据
	protected final static int _position_packageType = 2;
	protected final static int _position_version = 5;
	protected final static int _position_apMac = 11;
	protected final static int _position_apMacLength = 6;
	protected final static int _position_staNum = 19;

	// 报文体长度和起始位置
	protected final static int _position_messageLength = 22;
	protected final static int _length_messageLength = 2;
	protected final static int _position_messageStart = 24;

	// V2报文体长度和起始位置
	protected final static int _position_staNumLengthV2 = 2;
	protected final static int _position_messageLengthV2 = 23;
	protected final static int _length_messageLengthV2 = 4;
	protected final static int _position_messageStartV2 = 27;

	// 报文内容偏移量
	// protected final static int _offset_sta_type = 2;
	// protected final static int _length_sta_type = 1;
	// protected final static int _offset_sta_mac = 5;
	// protected final static int _length_sta_mac = 6;
	// protected final static int _offset_sta_rssi = 13;
	// protected final static int _length_sta_rssi = 1;
	// protected final static int _offset_sta_toa = 16;
	// protected final static int _length_sta_toa = 8;
	// protected final static int _offset_sta_interval = 27;
	// protected final static int _length_sta_interval = 8;
	// protected final static int _offset_sta_channel = 33;
	// protected final static int _length_sta_channel = 1;

	// 每个staion所占字节数
	protected final static int _length_perStation = 37;// v1版本每个station的数据占用字节数

	// 每个station报文中的type定义
	protected final static int _type_sta_type = 0;
	protected final static int _type_sta_mac = 1;
	protected final static int _type_sta_rssi = 2;
	protected final static int _type_sta_toa = 3;
	protected final static int _type_sta_interval = 4;
	protected final static int _type_sta_channel = 5;
}
