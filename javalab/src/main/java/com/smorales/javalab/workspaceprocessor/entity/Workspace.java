package com.smorales.javalab.workspaceprocessor.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Workspace")
@NamedQueries({
})
public class Workspace implements Serializable {

    private static final String PREFIX = "Workspace.";
    public static final String findById = PREFIX + "findById";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "json")
    private String json;

    public Workspace() {
    }

    public Workspace(int id, String json) {
        this.id = id;
        this.json = json;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String jsonWorkspace) {
        this.json = jsonWorkspace;
    }
}
