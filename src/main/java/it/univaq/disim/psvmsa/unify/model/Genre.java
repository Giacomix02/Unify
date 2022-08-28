package it.univaq.disim.psvmsa.unify.model;

import java.util.Objects;

public class Genre {
    private String name;
    private Integer id;
    public Genre(String name) {
        this.name = name;
    }

    public Genre(String name, Integer id) {
        this(name);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        Genre genre = (Genre) o;
        return Objects.equals(name, genre.name) &&
                Objects.equals(id, genre.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}
