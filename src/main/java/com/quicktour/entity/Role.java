package com.quicktour.entity;

import javax.persistence.*;
import java.util.Collection;

/**
 * @author Roman Lukash
 */
@Entity
@Table(name = "roles")
public class Role {

    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_AGENT = 2;
    public static final int ROLE_USER = 3;

    private int roleId;
    private String name;
    private Collection<User> users;

    public Role(int roleId) {
        this.roleId = roleId;
    }

    public Role() {
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Role{");
        sb.append("name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Id
    @GeneratedValue
    @Column(name = "role_id")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        if (roleId != role.roleId) return false;
        if (name != null ? !name.equals(role.name) : role.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roleId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
