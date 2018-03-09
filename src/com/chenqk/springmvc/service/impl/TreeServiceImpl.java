package com.chenqk.springmvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.chenqk.springmvc.dao.TreeDao;
import com.chenqk.springmvc.dao.UserDao;
import com.chenqk.springmvc.entity.Tree;
import com.chenqk.springmvc.service.TreeService;

@Service("TreeService")
public class TreeServiceImpl implements TreeService {
	
	@Autowired	
	@Qualifier("TreeDao")
	private TreeDao treeDao;
	
	/**
	 * 获取该节点信息
	 */
	@Override
	public Tree getNodeById(int id) {
		// TODO Auto-generated method stub
		return treeDao.getNodeById(id);
	}
	
	public TreeDao getTreeDao() {
		return treeDao;
	}

	public void setTreeDao(TreeDao treeDao) {
		this.treeDao = treeDao;
	}

	/**
	 * 获取该节点下的所有子节点
	 */
	@Override
	public List<Tree> getNodesById(int pid) {
		// TODO Auto-generated method stub
		return treeDao.getNodesById(pid);
	}

	@Override
	public boolean addTreeNode(Tree tree) {
		// TODO Auto-generated method stub
		return treeDao.addTreeNode(tree);
	}

	@Override
	public boolean updateTreeNode(Tree tree) {
		// TODO Auto-generated method stub
		return treeDao.updateTreeNode(tree);
	}

	@Override
	public boolean deleteTreeNode(int id) {
		// TODO Auto-generated method stub
		return treeDao.deleteTreeNode(id);
	}

	

}
