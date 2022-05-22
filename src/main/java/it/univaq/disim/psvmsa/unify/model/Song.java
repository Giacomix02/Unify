package it.univaq.disim.psvmsa.unify.model;

import java.util.ArrayList;
import java.util.List;

public class Song {
    private String name;
    private Album album;
    private List<Genre> genres = new ArrayList<Genre>();
    private Artist artist;
    private String lyrics;
    private Picture picture;

    private Integer id;


    public Song(String name, Album album, Artist artist, String lyrics, Picture picture, List<Genre> genres) {
        this.name = name;
        this.album = album;
        this.artist = artist;
        this.lyrics = lyrics;
        this.picture = picture;
        this.genres = genres;
    }

    public Song (String name, Album album, Artist artist, String lyrics, Picture picture, List<Genre> genres, Integer id) {
        this(name, album, artist, lyrics, picture, genres);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
