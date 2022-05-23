package it.univaq.disim.psvmsa.unify.model;

public class SingleArtist extends Artist {

    private Musician musician;

    public SingleArtist(String name, String biography, Picture picture) {
        super(name, biography, picture);
    }
    public SingleArtist(String name, String biography, Picture picture, Integer id) {
        super(name, biography, picture, id);
    }
}
