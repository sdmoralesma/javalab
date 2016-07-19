package com.smorales.javalab.business.processor.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "User")
@NamedQueries({
        @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Size(max = 45)
    @Column(name = "username")
    private String username;

    @Size(max = 45)
    @Column(name = "password")
    private String password;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "userid")
    private Set<Workspace> workspaceList;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Workspace> getWorkspaceList() {
        return workspaceList;
    }

    public void setWorkspaceList(Set<Workspace> workspaceList) {
        this.workspaceList = workspaceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return !(username != null ? !username.equals(user.username) : user.username != null);

    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User[ id=" + id + " ]";
    }

}
