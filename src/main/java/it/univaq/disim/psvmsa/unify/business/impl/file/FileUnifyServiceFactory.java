package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.*;

import java.io.File;
import java.nio.file.Paths;

public class FileUnifyServiceFactory extends UnifyServiceFactory {

    private final String BASE_PATH = Paths.get("")
            .toAbsolutePath()
            + File.separator + "data" + File.separator;

    private final FileGenreServiceImpl fileGenreService = new FileGenreServiceImpl(BASE_PATH + "genres.txt");
    private final FilePictureServiceImpl filePictureService = new FilePictureServiceImpl(BASE_PATH + "pictures.txt", BASE_PATH + "images/");
    private final FileAlbumServiceImpl fileAlbumService;
    private final FileArtistServiceImpl fileArtistService;
    private final FilePlaylistServiceImpl filePlaylistService;
    private final FileUserServiceImpl fileUserService;
    private final FileSongServiceImpl fileSongService;

    public FileUnifyServiceFactory(){
        fileArtistService = new FileArtistServiceImpl(
                BASE_PATH + "artists.txt",
                BASE_PATH + "group_artists.txt",
                BASE_PATH + "images_relation.txt",
                filePictureService
        );
        fileSongService = new FileSongServiceImpl(
                BASE_PATH + "songs.txt",
                BASE_PATH + "song_relation",
                fileArtistService,
                filePictureService,
                fileGenreService
        );
        fileUserService = new FileUserServiceImpl(
                BASE_PATH + "users.txt"
        );
        filePlaylistService = new FilePlaylistServiceImpl(
                BASE_PATH + "playlist.txt",
                BASE_PATH + "playlist_relation.txt",
                fileSongService,
                fileUserService
        );
        fileAlbumService = new FileAlbumServiceImpl(
                BASE_PATH + "albums.txt",
                BASE_PATH + "album_relation.txt",
                fileSongService,
                fileArtistService,
                fileGenreService
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
