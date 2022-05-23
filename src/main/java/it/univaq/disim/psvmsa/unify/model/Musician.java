package it.univaq.disim.psvmsa.unify.model;

public class Musician {
    private String name;
    private Integer id;


    public Musician(String name, Integer id) {
        this(name);
        this.id = id;
    }

    public Musician(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

