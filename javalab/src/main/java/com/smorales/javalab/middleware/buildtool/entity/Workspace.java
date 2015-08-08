package com.smorales.javalab.middleware.buildtool.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity(name = "workspace")
public class Workspace implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "json_workspace")
    private String jsonWorkspace;

    public Workspace() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJsonWorkspace() {
        return jsonWorkspace;
    }

    public void setJsonWorkspace(String jsonWorkspace) {
        this.jsonWorkspace = jsonWorkspace;
    }
}
