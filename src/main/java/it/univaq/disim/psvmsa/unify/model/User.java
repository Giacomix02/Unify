package it.univaq.disim.psvmsa.unify.model;


import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private Integer id;
    private List<Playlist> playlists = new ArrayList<Playlist>();

    public User(String username, String password, Integer id) {
        this(username, password);
        this.id = id;

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public int getId() {
        return this.id;
    }

    public List<Playlist> getPlaylists() {
        return this.playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void addPlaylist(Playlist playlist) {
        this.playlists.add(playlist);
    }

    public void removePlaylist(Playlist playlist) {
        this.playlists.remove(playlist);
    }


    public String toString() {
        return String.format("User{username='%s', password='%s', id=%d}", this.username, this.password, this.id);
    }
}
