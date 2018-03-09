package com.chenqk.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chenqk.springmvc.dao.UserDao;
import com.chenqk.springmvc.entity.UserMessage;
import com.chenqk.springmvc.service.UserService;

@Service("UserService")
public class UserServiceImpl implements UserService{

	@Autowired	
	@Qualifier("UserDao")
	private UserDao userDao;
	
	
	public UserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public UserMessage selectUserByNameAndPwd(UserMessage user) {
		
		return userDao.selectUserByNameAndPwd(user);
	}


	public List<UserMessage> selectAllUser() {
		return userDao.selectAllUser();
	}

}
