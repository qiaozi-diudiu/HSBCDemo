package com.qz.entities;

import com.qz.EncryptUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    private String username;

    private String password;

    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password) {
        this(username, password, new HashSet<>());
    }

    public User(String username, String password, Set<Role> roles) {
        this.username = username;
        this.password = EncryptUtil.encrypt(password);
        this.roles = roles;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!Objects.equals(username, user.username)) return false;
        return Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }
}
