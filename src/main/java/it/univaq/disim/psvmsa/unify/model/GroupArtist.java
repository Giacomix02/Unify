package it.univaq.disim.psvmsa.unify.model;

import java.util.ArrayList;
import java.util.List;

public class GroupArtist extends Artist {

    private List<Musician> musicians = new ArrayList<Musician>();

    public GroupArtist(Integer id,String name, String biography) {
        super(id, name, biography);
    }
    public GroupArtist(Integer id, String name, String biography, Picture picture) {
        super(id,name,biography,picture);
    }
}
