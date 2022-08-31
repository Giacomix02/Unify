package it.univaq.disim.psvmsa.unify.model;

import java.util.Objects;

public class Artist {

    private Integer id;

    private String name;

    private String biography;

    private Picture picture;

    public Artist(Integer id, String name, String biography) {
        this.id = id;
        this.name = name;
        this.biography = biography;
    }

    public Artist(Integer id, String name, String biography, Picture picture) {
        this(id, name, biography);
        this.picture = picture;
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

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return Objects.equals(name,artist.name) &&
                Objects.equals(id, artist.id) &&
                Objects.equals(biography, artist.biography) &&
                Objects.equals(picture, artist.picture);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, id, biography, picture);
    }
}
