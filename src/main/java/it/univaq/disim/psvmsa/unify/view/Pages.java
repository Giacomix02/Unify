package it.univaq.disim.psvmsa.unify.view;

public enum Pages {
    ALBUM("album"),
    ALBUMS("albums");

    private final String name;

    Pages(String name) {
        this.name = name;
    }
    public String toString(){
        return name;
    }
}
