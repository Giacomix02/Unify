package it.univaq.disim.psvmsa.unify.model;

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
}
