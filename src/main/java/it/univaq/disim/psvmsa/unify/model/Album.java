package it.univaq.disim.psvmsa.unify.model;

import java.util.List;
import java.util.Objects;

public class Album {
    private String name;
    private Integer id;
    private List<Song> songs;

    private Artist artist;
    public Album(String name, List<Song> songs, Artist artist) {
        this.name = name;
        this.songs = songs;
        this.artist = artist;
    }
    public Album(String name,List<Song> songs, Artist artist, Integer id) {
        this.name = name;
        this.id = id;
        this.songs = songs;
        this.artist = artist;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
    public List<Song> getSongs() {
        return songs;
    }
    public void setArtist(Artist artist) {
        this.artist = artist;
    }
    public Artist getArtist() {
        return artist;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return Objects.equals(name,album.name) &&
                Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,id);
    }

}
