package it.univaq.disim.psvmsa.unify.model;


import java.util.*;

public class User {
    private String username;
    private String password;
    private Integer id;

    public User(String username, String password, Integer id) {
        this(username, password);
        this.id = id;

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Integer getId() {
        return this.id;
    }

    public String toString() {
        return String.format("User{username='%s', password='%s', id=%d}", this.username, this.password, this.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username,user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, id);
    }
}
