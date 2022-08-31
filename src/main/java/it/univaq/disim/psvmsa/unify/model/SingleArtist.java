package it.univaq.disim.psvmsa.unify.model;

public class SingleArtist extends Artist {

    private Musician musician;

    public SingleArtist(Integer id,String name, String biography) {
        super(id, name, biography);
    }
    public SingleArtist(Integer id, String name, String biography, Picture picture) {
        super(id,name,biography,picture);
    }
}
