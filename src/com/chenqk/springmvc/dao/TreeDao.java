package com.chenqk.springmvc.dao;

import java.util.List;

import com.chenqk.springmvc.entity.Tree;

public interface TreeDao {
	/**
	 * 获取当前节点id所对应的tree对象
	 * @param id
	 * @return tree 实例对象
	 */
	public Tree getNodeById(int id);
	/**
	 * 获取当前节点下的所有子节点，不包括二级以后的节点
	 * @param pid 父节点
	 * @return list 子节点对象
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
