package it.univaq.disim.psvmsa.unify.model;

import java.util.ArrayList;
import java.util.List;

public class GroupArtist extends Artist {

    private List<Artist> artists;

    public GroupArtist(String name, String biography, Picture picture, List<Artist> artists) {
        super(name, biography, picture);
        this.artists = artists;
    }
    public GroupArtist(String name, String biography, Picture picture, List<Artist> artists, Integer id) {
        super(name, biography, picture, id);
        this.artists = artists;
    }


    public List<Artist> getArtists(){
        return this.artists;
    }
}
