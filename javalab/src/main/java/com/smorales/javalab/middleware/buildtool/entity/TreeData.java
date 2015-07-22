package com.smorales.javalab.middleware.buildtool.entity;

import com.smorales.javalab.middleware.buildtool.rest.Cursor;

import java.util.List;

public class TreeData {

    private long id;
    private String name;
    private String type;
    private String code;
    private Cursor cursor;
    private List<TreeData> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    public List<TreeData> getChildren() {
        return children;
    }

    public void setChildren(List<TreeData> children) {
        this.children = children;
    }
}
