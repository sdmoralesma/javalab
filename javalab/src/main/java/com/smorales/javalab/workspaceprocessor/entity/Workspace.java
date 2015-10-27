package com.smorales.javalab.workspaceprocessor.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Workspace")
@NamedQueries({
        @NamedQuery(name = Workspace.findAll, query = "SELECT w FROM Workspace w")
})
public class Workspace implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String PREFIX = "Workspace.";
    public static final String findAll = PREFIX + "findAll";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    @Size(max = 65535)
    @Column(name = "json")
    private String json;

    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "Workspace_Tag",
            joinColumns = @JoinColumn(name = "id_workspace"),
            inverseJoinColumns = @JoinColumn(name = "id_tag"))
    private Set<Tag> tags;

    @JoinColumn(name = "User_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User userid;

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

    public void setJson(String json) {
        this.json = json;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Workspace)) {
            return false;
        }
        Workspace other = (Workspace) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Workspace[ id=" + id + " ]";
    }

}
