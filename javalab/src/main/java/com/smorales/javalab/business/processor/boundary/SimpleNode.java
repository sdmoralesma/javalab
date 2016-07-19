package com.smorales.javalab.business.processor.boundary;

import java.util.Objects;

public class SimpleNode {

    public enum Type {
        FILE, FOLDER, UNKNOWN
    }

    private final String id;
    private final Type type;
    private final String label;
    private final String data;
    private final String parentId;

    public SimpleNode(String id) {
        this.id = id;
        this.type = Type.UNKNOWN;
        this.label = "";
        this.data = "";
        this.parentId = null;
    }

    public SimpleNode(String id, Type type, String label, String data, String parentId) {
        Objects.requireNonNull(id);
        this.id = id;
        this.type = type;
        this.label = label;
        this.data = data;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Type getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public String getParentId() {
        return parentId;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleNode that = (SimpleNode) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public String toString() {
        return "SimpleNode{" +
                "label='" + label + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
