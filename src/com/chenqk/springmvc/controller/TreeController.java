package com.chenqk.springmvc.controller;  
  
  
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chenqk.springmvc.entity.Tree;
import com.chenqk.springmvc.service.TreeService;
/** 
 * 控制类 
 * @author chenqk 
 * 
 */  
@Controller    
@RequestMapping("/")  
public class TreeController{  
  
      
    @Autowired  
    @Qualifier("TreeService")  
    private TreeService treeService;  
      
          
      
    /** 
     * 初始化所有的树形节点 
     * @throws UnsupportedEncodingException  
     */  
    @RequestMapping(value="/getNodesById")  
    public void getNodesById(@RequestParam int id ,HttpServletResponse response) throws UnsupportedEncodingException{  
        
        System.out.println("kaishi");  
        String str ="";  
        StringBuilder json = new StringBuilder();  
          
        // 获得根节点  
        Tree treeRoot = treeService.getNodeById(id);  
        // 拼接根节点  
        json.append("[");  
        json.append("{\"id\":" +String.valueOf(treeRoot.getId()));   
        json.append(",\"text\":\"" +treeRoot.getText() + "\"");   
        json.append(",\"state\":\"open\"");  
        // 获取根节点下的所有子节点  
        List<Tree> treeList = treeService.getNodesById(id);  
        // 遍历子节点下的子节点  
        if(treeList!=null && treeList.size()!=0){  
            json.append(",\"children\":[");  
            for (Tree t : treeList) {  
                  
                json.append("{\"id\":" +String.valueOf(t.getId()));   
                json.append(",\"text\":\"" +t.getText() + "\"");   
                json.append(",\"state\":\"open\"");   
                  
                // 该节点有子节点  
                // 设置为关闭状态,而从构造异步加载tree  
              
                List<Tree> tList = treeService.getNodesById(t.getId());  
                if(tList!=null && tList.size()!=0){// 存在子节点  
                     json.append(",\"children\":[");  
                     json.append(dealJsonFormat(tList));// 存在子节点的都放在一个工具类里面处理了  
                     json.append("]");  
                }  
                json.append("},");  
            }  
            str = json.toString();  
            str = str.substring(0, str.length()-1);  
            str+="]}]";  
              
        }  
        try {  
              
            System.out.println("输出json数据"+str);  
            response.getWriter().print(str);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 添加新节点 
     * @param area 节点名称 
     * @param pid 父节点id 
     * @param request 
     * @param response 
     */  
    @RequestMapping(value="/addTreeNode")  
    public void addTreeNode(@RequestParam String area,String pid,HttpServletRequest request,HttpServletResponse response){  
        System.out.println("area="+area+",pid="+pid);  
        Tree tree = new Tree();  
        tree.setPid(Integer.parseInt(pid));  
        tree.setText(area);  
        tree.setAttributes("showAreaList.do?");  
        treeService.addTreeNode(tree);  
    }  
      
    /** 
     * 修改节点名称 
     * @param area 节点名称 
     * @param id 节点id 
     * @param request 
     * @param response 
     */  
    @RequestMapping(value="/updTreeNode")  
    public void updTreeNode(@RequestParam String area,String id,HttpServletRequest request,HttpServletResponse response){  
        Tree tree = new Tree();  
        tree.setId(Integer.parseInt(id));  
        tree.setText(area);  
        treeService.updateTreeNode(tree);  
  
    }  
      
    /** 
     * 删除节点 
     * @param id 节点id 
     * @param pid 
     * @param request 
     * @param response 
     */  
    @RequestMapping(value="/delTreeNode")  
    public void delTreeNode(@RequestParam String id,HttpServletRequest request,HttpServletResponse response){  
        treeService.deleteTreeNode(Integer.parseInt(id));
  
    }  
      
    /** 
     * 处理数据集合，将数据集合转为符合格式的json 
     * @param tList 参数 
     * @return json字符串 
     */  
    public String dealJsonFormat(List<Tree> tList){  
        StringBuilder json = new StringBuilder();  
        for (Tree tree : tList) {  
            json.append("{\"id\":" +String.valueOf(tree.getId()));   
            json.append(",\"text\":\"" +tree.getText() + "\"");   
            json.append(",\"attributes\":\""+tree.getAttributes()+"\"");   
            json.append("},");  
        }  
        String str = json.toString();  
        str = str.substring(0, str.length()-1);  
          
        System.out.println("---------"+str);  
        return str;  
    }  
  
      
    public TreeService getTreeService() {  
        return treeService;  
    }  
  
    public void setTreeService(TreeService treeService) {  
        this.treeService = treeService;  
    }  
  
}  