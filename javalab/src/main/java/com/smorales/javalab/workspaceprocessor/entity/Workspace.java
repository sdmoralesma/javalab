package com.smorales.javalab.workspaceprocessor.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Workspace")
@NamedQueries({
        @NamedQuery(name = Workspace.findById, query = "select w from Workspace w where w.path = :id"),
})
public class Workspace implements Serializable {

    private static final String PREFIX = "Workspace.";
    public static final String findById = PREFIX + "findById";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "path")
    private String path;

    @Column(name = "json")
    private String json;

    public Workspace() {
    }

    public Workspace(int id, String path, String json) {
        this.id = id;
        this.path = path;
        this.json = json;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String base62) {
        this.path = base62;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String jsonWorkspace) {
        this.json = jsonWorkspace;
    }
}
