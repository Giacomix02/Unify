package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.*;

public class RAMUnifyServiceFactory extends UnifyServiceFactory {

    private RAMGenreServiceImpl ramGenreService = new RAMGenreServiceImpl();
    private RAMPlaylistServiceImpl ramPlaylistService = new RAMPlaylistServiceImpl();
    private RAMPictureServiceImpl ramPictureService = new RAMPictureServiceImpl();
    private RAMSongServiceImpl ramSongService = new RAMSongServiceImpl();
    private RAMUserServiceImpl ramUserService = new RAMUserServiceImpl();



    @Override
    public AlbumService getAlbumService(){
        return null;
    }

    @Override
    public ArtistService getArtistService() {
        return null;
    }

    @Override
    public GenreService getGenreService(){
        return ramGenreService;
    }

    @Override
    public PlaylistService getPlaylistService(){
        return ramPlaylistService;
    }

    @Override
    public PictureService getPictureService(){
        return ramPictureService;
    }

    @Override
    public SongService getSongService() {
        return ramSongService;
    }

    @Override
    public UserService getUserService(){
        return ramUserService;
    }
}