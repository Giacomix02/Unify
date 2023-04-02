package it.univaq.disim.psvmsa.unify.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Song {
    private String name;
    private Genre genre;
    private Artist artist;
    private String lyrics;
    private Picture picture;
    private Integer id;
    private InputStream content;
    private byte[] cache;


    public Song(String name, Artist artist, String lyrics, Picture picture, Genre genre, InputStream content) {
        this.name = name;
        this.artist = artist;
        this.lyrics = lyrics;
        this.picture = picture;
        this.genre = genre;
        this.content = content;
    }

    public Song (String name, Artist artist, String lyrics, Picture picture, Genre genre, InputStream content, Integer id) {
        this(name, artist, lyrics, picture, genre, content);
        this.id = id;
    }
    public void setContent(InputStream content) {
        this.content = content;
        cache = null;
    }
    public InputStream toStream()  {
        if (cache == null) {
            try {
                cache = content.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ByteArrayInputStream(this.cache);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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
                Objects.equals(artist, song.artist) &&
                Objects.equals(lyrics, song.lyrics) &&
                Objects.equals(picture, song.picture) &&
                Objects.equals(genre, song.genre) &&
                Objects.equals(id, song.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(name, artist,
                            lyrics, picture, genre, id);
    }

}
