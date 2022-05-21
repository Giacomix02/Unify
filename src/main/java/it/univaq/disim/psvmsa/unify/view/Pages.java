package it.univaq.disim.psvmsa.unify.view;

public enum Pages {
    ALBUM("album"),
    SONG("song"),
    ARTIST("artist"),
    HOME("home"),
    ALBUMS("albums"),
    SONGS("songs"),
    ARTISTS("artists"),
    GENRES("genres");
    private final String name;

    Pages(String name) {
        this.name = name;
    }
    public String toString(){
        return name;
    }
}
