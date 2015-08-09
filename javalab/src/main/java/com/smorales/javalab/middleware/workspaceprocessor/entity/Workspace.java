package com.smorales.javalab.middleware.workspaceprocessor.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Workspace")
@NamedQueries({
        @NamedQuery(name = Workspace.findFirstRow, query = "select w from Workspace w order by w.id asc"),
        @NamedQuery(name = Workspace.findIdLastRow, query = "select w.id from Workspace w order by w.id desc"),
        @NamedQuery(name = Workspace.findByBase62, query = "select w from Workspace w where w.base62 = :base62"),
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
    private String base62;

    @Column(name = "workspace")
    private String workspace;

    public Workspace() {
    }

    public Workspace(int id, String base62, String workspace) {
        this.id = id;
        this.base62 = base62;
        this.workspace = workspace;
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
