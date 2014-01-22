package com.quicktour.entity;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "roles", schema = "", catalog = "quicktour")
public class Role {

    public static final int ADMIN_ROLE = 1;
    public static final int AGENT_ROLE = 2;
    public static final int USER_ROLE = 3;

    private int roleId;
    private String role;
    private Collection<User> usersByRoleId;

    @Column(name = "RoleId")
    @Id
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Column(name = "Role")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @OneToMany(mappedBy = "roleId")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Collection<User> getUsersByRoleId() {
        return usersByRoleId;
    }

    public void setUsersByRoleId(Collection<User> usersByRoleId) {
        this.usersByRoleId = usersByRoleId;
    }

}
