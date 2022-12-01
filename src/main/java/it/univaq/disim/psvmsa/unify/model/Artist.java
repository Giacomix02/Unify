package it.univaq.disim.psvmsa.unify.model;

import java.util.List;
import java.util.Objects;

public class Artist {

    private Integer id;

    private String name;

    private String biography;

    private List<Picture> pictures;

    public Artist(String name, String biography, List<Picture> pictures){
        this.name = name;
        this.biography = biography;
        this.pictures = pictures;
    }
    public Artist(String name, String biography, List<Picture> pictures, Integer id){
        this.name = name;
        this.biography = biography;
        this.pictures = pictures;
        this.id = id;
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

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures( List<Picture> pictures) {
        this.pictures = pictures;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Artist)) return false;
        Artist artist = (Artist) o;
        return Objects.equals(name,artist.name) &&
                Objects.equals(id, artist.id) &&
                Objects.equals(biography, artist.biography) &&
                Objects.equals(pictures, artist.pictures);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, id, biography, pictures);
    }
}
