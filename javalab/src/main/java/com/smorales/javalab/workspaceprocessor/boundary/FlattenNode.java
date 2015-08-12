package com.smorales.javalab.workspaceprocessor.boundary;

import java.nio.file.Path;

public class FlattenNode {

    private String id;
    private Path path;
    private String type;
    private String code;

    public FlattenNode(String id, Path path, String type, String code) {
        this.id = id;
        this.path = path;
        this.type = type;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlattenNode that = (FlattenNode) o;

        return path.equals(that.path);

    }

    @Override
    public int hashCode() {
        return path.hashCode();
    }
}
