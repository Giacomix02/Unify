package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.*;

import java.io.File;
import java.nio.file.Paths;

public class FileUnifyServiceFactory extends UnifyServiceFactory {
    private final String BASE_PATH = Paths.get("")
            .toAbsolutePath()
            + File.separator + "data" + File.separator;

    private FileGenreServiceImpl fileGenreService = new FileGenreServiceImpl(BASE_PATH + "genres.txt");
    private FilePictureServiceImpl filePictureService = new FilePictureServiceImpl();

    private FileSongServiceImpl fileSongService = new FileSongServiceImpl();

    private FilePlaylistServiceImpl filePlaylistService = new FilePlaylistServiceImpl();

    private FileUserServiceImpl fileUserService = new FileUserServiceImpl(BASE_PATH + "users.txt");



    private AlbumService albumService;

    private ArtistService artistService;

    private GenreService genreService;

    private PictureService pictureService;
    private SongService songService;
    private PlaylistService playlistService;
    private UserService userService;



    @Override
    public AlbumService getAlbumService() {
        return null;
    }

    @Override
    public ArtistService getArtistService() {
        return null;
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
        return songService;
    }

    @Override
    public PlaylistService getPlaylistService() {
        return playlistService;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

}
