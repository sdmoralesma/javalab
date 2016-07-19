package com.smorales.javalab.business.processor.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Tag")
@NamedQueries({
        @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t")
})
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "tags")
    private Set<Workspace> workspaces;

    public Tag() {
    }

    public Tag(Integer id) {
        this.id = id;
    }

    public Tag(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Workspace> getWorkspaceTagList() {
        return workspaces;
    }

    public void setWorkspaceTagList(Set<Workspace> workspaces) {
        this.workspaces = workspaces;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return !(name != null ? !name.equals(tag.name) : tag.name != null);
    }


    @Override
    public String toString() {
        return "Tag[ id=" + id + " ]";
    }

}
