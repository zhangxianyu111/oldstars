package com.demo.dto.log;

import java.util.ArrayList;
import java.util.List;

/**
 * Ztree树形目录节点
 * @author zje
 */
public class ZtreeNodeVO {
    // 最后一级名称
    private String name;
    // 全路径名
    private String fullName;
    // 日志級別,注意不能取名为level，与Ztree中默认节点字段冲突
//    	private String type;
    // 子节点列表
    private List<ZtreeNodeVO> children= null;
    // 图标
    private String icon;


    public void addChild(ZtreeNodeVO node) {
        if(children ==null) {
            children = new ArrayList<ZtreeNodeVO>();
        }
        children.add(node);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public List<ZtreeNodeVO> getChildren() {
        return children;
    }
    public void setChildren(List<ZtreeNodeVO> children) {
        this.children = children;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
}
