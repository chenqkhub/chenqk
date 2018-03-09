package com.chenqk.springmvc.service;

import java.util.List;

import com.chenqk.springmvc.entity.UserMessage;

public interface UserService {
	public UserMessage selectUserByNameAndPwd(UserMessage user);
	public List<UserMessage> selectAllUser();
}
