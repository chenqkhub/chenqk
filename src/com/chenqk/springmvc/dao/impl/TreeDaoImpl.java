package com.chenqk.springmvc.dao.impl;

import java.util.List;




import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.chenqk.springmvc.dao.TreeDao;
import com.chenqk.springmvc.entity.Tree;

@Repository("TreeDao")
public class TreeDaoImpl implements TreeDao {
	
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	
	
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}


	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public Tree getNodeById(int id) {
		SqlSession sqlSession =(SqlSession) this.getSqlSessionFactory().openSession();
		return sqlSession.selectOne("com.chenqk.springmvc.entity.UserMessage.selectUserByNameAndPwd", id);
	}


	@Override
	public List<Tree> getNodesById(int pid) {
		SqlSession sqlSession =(SqlSession) this.getSqlSessionFactory().openSession();
		return sqlSession.selectOne("com.chenqk.springmvc.entity.UserMessage.selectUserByNameAndPwd", pid);
	}


	@Override
	public boolean addTreeNode(Tree tree) {
		SqlSession sqlSession =(SqlSession) this.getSqlSessionFactory().openSession();
		return sqlSession.selectOne("com.chenqk.springmvc.entity.UserMessage.selectUserByNameAndPwd", tree);
	}


	@Override
	public boolean updateTreeNode(Tree tree) {
		SqlSession sqlSession =(SqlSession) this.getSqlSessionFactory().openSession();
		return sqlSession.selectOne("com.chenqk.springmvc.entity.UserMessage.selectUserByNameAndPwd", tree);
	}


	@Override
	public boolean deleteTreeNode(int id) {
		SqlSession sqlSession =(SqlSession) this.getSqlSessionFactory().openSession();
		return sqlSession.selectOne("com.chenqk.springmvc.entity.UserMessage.selectUserByNameAndPwd", id);
	}

}
