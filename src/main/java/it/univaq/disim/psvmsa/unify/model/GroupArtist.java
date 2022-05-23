package it.univaq.disim.psvmsa.unify.model;

import java.util.ArrayList;
import java.util.List;

public class GroupArtist extends Artist {

    private List<Musician> musicians = new ArrayList<Musician>();

    public GroupArtist(String name, String biography, Picture picture) {
        super(name, biography, picture);
    }
    public GroupArtist(String name, String biography, Picture picture, Integer id) {
        super(name, biography, picture, id);
    }
}
