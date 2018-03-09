package com.chenqk.springmvc.service;

import java.util.List;

import com.chenqk.springmvc.entity.Tree;

public interface TreeService {
	/**
	 * 获取当前节点信息
	 * @param id 节点id
	 * @return tree实例对象
	 */
	public Tree getNodeById(int id);
	
	/**
	 * 获取当前节点下的所有子节点，不包括二级及以下节点
	 * @param pid
	 * @return
	 */
	public List<Tree> getNodesById(int pid);
	
	/**
	 * 添加新的节点
	 * @param tree 实例对象
	 * @return 成功失败
	 */
	public boolean addTreeNode(Tree tree);
	
	/**
	 * 修改节点信息，一般修改显示名
	 * @param tree 实例对象
	 * @return 成功失败
	 */
	public boolean updateTreeNode(Tree tree);
	
	/**
	 * 删除节点
	 * @param id 实例id
	 * @return 删除结果
	 */
	public boolean deleteTreeNode(int id);
}
