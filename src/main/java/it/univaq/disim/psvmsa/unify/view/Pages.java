package it.univaq.disim.psvmsa.unify.view;

public enum Pages {
    ALBUM("album"),
    SONG("song"),
    ARTIST("artist"),
    HOME("home"),
    ALBUMS("albums"),
    SONGS("songs"),
    ARTISTS("artists"),
    GENRES("genres"),
    PLAYLISTS("playlists"),
    MANAGE_USERS("manageUsers"),
    ADDGENRE("addGenre"),
    ADDSONG("addSong"),
    ADDARTIST("addArtist"),
    ADDUSER("addUser"),
    EDITSONG("editSong"),
    EDITALBUM("editAlbum"),
    EDITUSER("editUser"),
    ARTISTDETAILS("artistDetails"),
    ADDPLAYLIST("addPlaylist");
    private final String name;

    Pages(String name) {
        this.name = name;
    }
    public String toString(){
        return name;
    }
}
