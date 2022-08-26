package it.univaq.disim.psvmsa.unify.model;

public class Album {
    private String name;
    private Integer id;

    public Album(String name) {
        this.name = name;
    }

    public Album(String name, Integer id) {
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


    // experimental functions
    public boolean equals(Object o ){
        if(o instanceof Album){
            Album album = (Album)o;
            return name.equals(album.name) && id.equals(album.id);
        }
        return false;
    }

    public void setFrom(Album album){
        this.name = album.name;
        this.id = album.id;
    }
}
