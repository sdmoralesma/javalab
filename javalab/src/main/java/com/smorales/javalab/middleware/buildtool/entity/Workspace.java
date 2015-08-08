package com.smorales.javalab.middleware.buildtool.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Workspace")
@NamedQueries({
        @NamedQuery(name = "Workspace.findFirstRow", query = "select w from Workspace w order by w.id asc"),
        @NamedQuery(name = "Workspace.findIdLastRow", query = "select w.id from Workspace w order by w.id desc"),
        @NamedQuery(name = "Workspace.findByBase62", query = "select w from Workspace w where w.base62 = :base62"),
})
public class Workspace implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "base62")
    private String base62;

    @Column(name = "workspace")
    private String workspace;

    public Workspace() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBase62() {
        return base62;
    }

    public void setBase62(String base62) {
        this.base62 = base62;
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String jsonWorkspace) {
        this.workspace = jsonWorkspace;
    }
}
