package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;

public class RAMUnifyServiceFactory extends UnifyServiceFactory {
    private RAMGenreServiceImpl ramGenreService = new RAMGenreServiceImpl();
    private RAMPictureServiceImpl ramPictureService = new RAMPictureServiceImpl();
    private RAMSongServiceImpl ramSongService = new RAMSongServiceImpl();
    private RAMPlaylistServiceImpl ramPlaylistService = new RAMPlaylistServiceImpl(ramSongService);
    private RAMUserServiceImpl ramUserService = new RAMUserServiceImpl();
    private  RAMAlbumServiceImpl ramAlbumService = new RAMAlbumServiceImpl(ramSongService);
    private  RAMArtistServiceImpl ramArtistService = new RAMArtistServiceImpl();

    public RAMUnifyServiceFactory(){
        try{
            User adminUser = new Admin("admin","admin");
            User user = new User("user","user");
            ramUserService.add(user);
            ramUserService.add(adminUser);

            Genre genre = new Genre(1,"Rock");
            Genre genre2 = new Genre(2,"Pop");
            ramGenreService.add(genre);
            ramGenreService.add(genre2);
        }catch(BusinessException e){
            e.printStackTrace();
        }
    }

    @Override
    public AlbumService getAlbumService(){
        return ramAlbumService;
    }

    @Override
    public ArtistService getArtistService() {
        return ramArtistService;
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
