package com.chenqk.springmvc.entity;

public class Tree {
	private int id;// 表id  
    private int pid;// 父节点id  
    private String text;// 节点内容  
    private String attributes;// 请求路径  
    public int getId() {  
        return id;  
    }  
    public void setId(int id) {  
        this.id = id;  
    }  
    public int getPid() {  
        return pid;  
    }  
    public void setPid(int pid) {  
        this.pid = pid;  
    }  
    public String getText() {  
        return text;  
    }  
    public void setText(String text) {  
        this.text = text;  
    }  
    public String getAttributes() {  
        return attributes;  
    }  
    public void setAttributes(String attributes) {  
        this.attributes = attributes;  
    }  
}
