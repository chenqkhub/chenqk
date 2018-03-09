package com.chenqk.springmvc.controller;


import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.chenqk.springmvc.entity.UserMessage;
import com.chenqk.springmvc.service.UserService;
import com.chenqk.springmvc.util.DBUtil;
import com.chenqk.springmvc.util.LocationVo;
import com.chenqk.springmvc.util.StationEntity;

@Controller
@RequestMapping("/")  
public class UserController{
	
	@Autowired
	@Qualifier("UserService")
	private UserService userService;
	
	private UserMessage user;
	private List<UserMessage>userList;
	private String staMac;
	
	
	public String getStaMac() {
		return staMac;
	}


	public void setStaMac(String staMac) {
		this.staMac = staMac;
	}


	public List<UserMessage> getUserList() {
		return userList;
	}


	public void setUserList(List<UserMessage> userList) {
		this.userList = userList;
	}


	public UserMessage getUser() {
		return user;
	}


	public void setUser(UserMessage user) {
		this.user = user;
	}


	
	
	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	@RequestMapping(method = RequestMethod.POST, value = "/login")
	public ModelAndView login(@RequestParam("user") UserMessage user){
		UserMessage userinfo =new UserMessage();
		
		userinfo =userService.selectUserByNameAndPwd(user);
		
		if(userinfo!=null){
			return new ModelAndView("main","userList",userList);
		}else {
			return new ModelAndView("main","userList",userList);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/showListener")
	@ResponseBody
	public void showListener(@RequestParam("staMac")  String staMac, PrintWriter printWriter){
		System.out.println("启动监听--------------------"+staMac);		
		//Map<String,Map<String, Integer>> mapInfo = new HashMap<String,Map<String, Integer>>()  ;
		
		// 创建集合存放ap
		List<List<String>> apList = new ArrayList<List<String>>();
		// 数据值
		List<List<Double>> rssiList = new ArrayList<List<Double>>();
		
		
		// 获取时间轴坐标
		List<String> collTimeList = DBUtil.searchAllApByColltime();
		
		// 实体类封装集合
		StationEntity stationEntity = new StationEntity();
		
		for (int i = 0; i < 3; i++) {
			int num = 0;
			// 创建集合存放ap
			List<String> apList1 = new ArrayList<String>();
			List<Double> rssiList1 = new ArrayList<Double>();
			String collTime = collTimeList.get(i);
			List<LocationVo> locationVo = DBUtil.getAllStaMacByTime(collTime);
			for (int j = 0; j <5; j++) {
				if(num<5){
					num++;
					apList1.add(locationVo.get(j).getApMac());
					rssiList1.add(locationVo.get(j).getRSSI());
				}else break;
			}
			apList.add(apList1);
			rssiList.add(rssiList1);
		}
		stationEntity.setApList(apList);
		stationEntity.setCollTimeList(collTimeList);
		stationEntity.setRssiList(rssiList);
		
		JSONObject jSONObject = JSONObject.fromObject(stationEntity);
		System.out.println(jSONObject.toString());
		printWriter.print(jSONObject);
		 
		//return jSONObject ;
	}
}
