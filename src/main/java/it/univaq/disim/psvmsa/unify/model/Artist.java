package it.univaq.disim.psvmsa.unify.model;

public class Artist {

    private String name;

    private Integer id;

    private String biography;

    private Picture picture;

    public Artist(String name, String biography, Picture picture) {
        this.name = name;
        this.biography = biography;
        this.picture = picture;
    }

    public Artist(String name, String biography, Picture picture, Integer id) {
        this(name, biography, picture);
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

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}
