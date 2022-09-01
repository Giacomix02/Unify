package it.univaq.disim.psvmsa.unify.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Song {
    private String name;
    private Album album;
    private List<Genre> genres;
    private Artist artist;
    private String lyrics;
    private Picture picture;
    private Integer id;


    public Song(String name){
        this.name = name;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return Objects.equals(name,song.name) &&
                Objects.equals(album, song.album) &&
                Objects.equals(artist, song.artist) &&
                Objects.equals(lyrics, song.lyrics) &&
                Objects.equals(picture, song.picture) &&
                Objects.equals(genres, song.genres) &&
                Objects.equals(id, song.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, album, artist,
                            lyrics, picture, genres, id);
    }

}
