package com.chenqk.springmvc.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chenqk.springmvc.dao.UserDao;
import com.chenqk.springmvc.entity.UserMessage;

@Repository("UserDao")
public class UserDaoImpl implements UserDao{
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}


	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}


	public UserMessage selectUserByNameAndPwd(UserMessage user) {
		SqlSession sqlSession =(SqlSession) this.getSqlSessionFactory().openSession();
		System.out.println(user.getPassword()+"-------"+user.getUserName());
		return sqlSession.selectOne("com.chenqk.springmvc.entity.UserMessage.selectUserByNameAndPwd", user);
	}


	public List<UserMessage> selectAllUser() {
		SqlSession sqlSession =(SqlSession) this.getSqlSessionFactory().openSession();
		
		return sqlSession.selectList("com.chenqk.springmvc.entity.UserMessage.selectAllUser");
	}

}
