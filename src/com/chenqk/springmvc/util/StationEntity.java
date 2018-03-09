package com.chenqk.springmvc.util;

import java.util.List;

public class StationEntity {

	private String colltime;
	
	private List<Station> stationList;
	
	private List<List<String>> apList;
	
	private List<List<Double>> rssiList;
	
	private List<String> collTimeList;
	
	
	
	public List<List<String>> getApList() {
		return apList;
	}
	public void setApList(List<List<String>> apList) {
		this.apList = apList;
	}
	public List<List<Double>> getRssiList() {
		return rssiList;
	}
	public void setRssiList(List<List<Double>> rssiList) {
		this.rssiList = rssiList;
	}
	public List<String> getCollTimeList() {
		return collTimeList;
	}
	public void setCollTimeList(List<String> collTimeList) {
		this.collTimeList = collTimeList;
	}
	public String getColltime() {
		return colltime;
	}
	public void setColltime(String colltime) {
		this.colltime = colltime;
	}
	public List<Station> getStationList() {
		return stationList;
	}
	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}
}
