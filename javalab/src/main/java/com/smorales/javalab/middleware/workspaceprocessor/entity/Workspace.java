package com.smorales.javalab.middleware.workspaceprocessor.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Workspace")
@NamedQueries({
        @NamedQuery(name = Workspace.findFirstRow, query = "select w from Workspace w order by w.id asc"),
        @NamedQuery(name = Workspace.findIdLastRow, query = "select w.id from Workspace w order by w.id desc"),
        @NamedQuery(name = Workspace.findByBase62, query = "select w from Workspace w where w.path = :base62"),
})
public class Workspace implements Serializable {

    private static final String PREFIX = "Workspace.";
    public static final String findFirstRow = PREFIX + "findFirstRow";
    public static final String findIdLastRow = PREFIX + "findIdLastRow";
    public static final String findByBase62 = PREFIX + "findByBase62";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "base62")
    private String path;

    @Column(name = "workspace")
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
