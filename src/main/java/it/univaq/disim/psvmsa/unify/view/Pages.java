package it.univaq.disim.psvmsa.unify.view;

public enum Pages {
    HOME("home"),
    ALBUMS("albums"),
    SONGS("songs"),
    SONGSLIST("songsList"),
    ARTISTS("artists"),
    GENRES("genres"),
    PLAYLISTS("playlists"),
    MANAGE_USERS("manageUsers"),
    ADDGENRE("addGenre"),
    EDITARTIST("editArtist"),
    EDITSONG("editSong"),
    EDITALBUM("editAlbum"),
    EDITUSER("editUser"),
    ARTISTDETAILS("artistDetails"),
    SONGDETAILS("songDetails"),
    EDITPLAYLIST("editPlaylist");
    private final String name;

    Pages(String name) {
        this.name = name;
    }
    public String toString(){
        return name;
    }
}
