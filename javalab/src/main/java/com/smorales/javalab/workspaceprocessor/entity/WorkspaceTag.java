package com.smorales.javalab.workspaceprocessor.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Workspace_Tag")
@NamedQueries({
        @NamedQuery(name = "WorkspaceTag.findAll", query = "SELECT w FROM WorkspaceTag w")})
public class WorkspaceTag implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "id_tag", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tag idTag;

    @JoinColumn(name = "id_workspace", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Workspace idWorkspace;

    public WorkspaceTag() {
    }

    public WorkspaceTag(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tag getIdTag() {
        return idTag;
    }

    public void setIdTag(Tag idTag) {
        this.idTag = idTag;
    }

    public Workspace getIdWorkspace() {
        return idWorkspace;
    }

    public void setIdWorkspace(Workspace idWorkspace) {
        this.idWorkspace = idWorkspace;
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
        if (!(object instanceof WorkspaceTag)) {
            return false;
        }
        WorkspaceTag other = (WorkspaceTag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smorales.javalab.workspaceprocessor.entity.WorkspaceTag[ id=" + id + " ]";
    }

}
