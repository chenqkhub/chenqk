package com.chenqk.springmvc.dao;
import java.util.List;

import com.chenqk.springmvc.entity.UserMessage;

/**
 * 测试框架：dao层
 * @author Administrator
 *
 */
public interface UserDao {
	public UserMessage selectUserByNameAndPwd(UserMessage user);
	public List<UserMessage> selectAllUser();
}
