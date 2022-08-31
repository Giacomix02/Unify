package it.univaq.disim.psvmsa.unify.model;

import java.util.Objects;

public class Album {
    private String name;
    private Integer id;

    public Album(Integer id) {
        this.id = id;
    }

    public Album(Integer id, String name) {
        this(id);
        this.name = name;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Album)) return false;
        Album album = (Album) o;
        return Objects.equals(name,album.name) &&
                Objects.equals(id, album.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,id);
    }

}
