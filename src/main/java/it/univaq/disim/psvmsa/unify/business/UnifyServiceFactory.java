package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.impl.file.FileUnifyServiceFactory;
import it.univaq.disim.psvmsa.unify.business.impl.ram.RAMUnifyServiceFactory;

public abstract class UnifyServiceFactory {

    //private static UnifyServiceFactory instance = new RAMUnifyServiceFactory();
    private static UnifyServiceFactory instance = new FileUnifyServiceFactory();

    public abstract AlbumService getAlbumService();

    public abstract ArtistService getArtistService();

    public abstract GenreService getGenreService();

    public abstract PlaylistService getPlaylistService();

    public abstract PictureService getPictureService();

    public abstract SongService getSongService();

    public abstract UserService getUserService();

    public static UnifyServiceFactory getInstance() {
        return instance;
    }

}
