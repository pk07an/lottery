package com.npc.lottery.util;

import java.io.Serializable;

/**
 * 用来构造页面上下拉列表实体对象类
 * 
 * @author none
 *
 */
public class ListEntity implements Serializable {

    private Long sortNum;//排序字段

    private String key;//填充下拉列表中的key

    private String name;//填充下拉列表中的name

    public Long getSortNum() {
        return sortNum;
    }

    public void setSortNum(Long sortNum) {
        this.sortNum = sortNum;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
