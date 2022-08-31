package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.*;

import java.io.File;
import java.nio.file.Paths;

public class FileUnifyServiceFactory extends UnifyServiceFactory {

    private final String BASE_PATH = Paths.get("")
            .toAbsolutePath()
            + File.separator + "data" + File.separator;

    private FileGenreServiceImpl fileGenreService = new FileGenreServiceImpl(BASE_PATH + "genres.txt");
    private FilePictureServiceImpl filePictureService = new FilePictureServiceImpl(BASE_PATH + "pictures.txt", BASE_PATH + "images/");
    private FileSongServiceImpl fileSongService;
    private FileAlbumServiceImpl fileAlbumService = new FileAlbumServiceImpl(BASE_PATH + "albums.txt");
    private FileArtistServiceImpl fileArtistService = new FileArtistServiceImpl(BASE_PATH + "artists.txt");
    private FilePlaylistServiceImpl filePlaylistService;
    private FileUserServiceImpl fileUserService = new FileUserServiceImpl(BASE_PATH + "users.txt");


    public FileUnifyServiceFactory(){
        filePlaylistService = new FilePlaylistServiceImpl(
                BASE_PATH + "playlist.txt",
                BASE_PATH + "playlist_relation.txt",
                fileSongService
        );
        fileSongService = new FileSongServiceImpl(
                BASE_PATH + "songs.txt",
                BASE_PATH + "song_relation.txt",
                BASE_PATH + "songs/",
                fileArtistService,
                fileAlbumService,
                filePictureService
        );
    }


    @Override
    public AlbumService getAlbumService() {
        return fileAlbumService;
    }

    @Override
    public ArtistService getArtistService() {
        return fileArtistService;
    }

    @Override
    public GenreService getGenreService() {
        return fileGenreService;
    }

    @Override
    public PictureService getPictureService() {
        return filePictureService;
    }

    @Override
    public SongService getSongService() {
        return fileSongService;
    }

    @Override
    public PlaylistService getPlaylistService() {
        return filePlaylistService;
    }

    @Override
    public UserService getUserService() {
        return fileUserService;
    }

}
