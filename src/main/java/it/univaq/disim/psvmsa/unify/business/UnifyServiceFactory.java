package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.business.AlbumService;

public abstract class UnifyServiceFactory {

    private AlbumService albumService;

    private ArtistService artistService;

    private GenreService genreService;

    private PictureService pictureService;

    private SongService songService;

    private UserService userService;

    private static UnifyServiceFactory instance;

    public abstract AlbumService getAlbumService();

    public abstract ArtistService getArtistService();

    public abstract GenreService getGenreService();

    public abstract PictureService getPictureService();

    public abstract SongService getSongService();

    public abstract UserService getUserService();

    public static UnifyServiceFactory getInstance() {
        return instance;
    }

}
