package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.Song;

public class FileSongServiceImpl implements SongService {
    private static class Schema{
        public final int SONG_ID = 0;
        public final int SONG_NAME = 1;
        public final int ARTIST_ID = 2;
        public final int ALBUM_ID = 3;
        public final int LYRICS = 4;
        public final int PICTURE_ID = 5;
    }
    private static class RelationSchema{
        public final int SONG_ID = 0;
        public final int GENRE_ID = 1;
    }
    private IndexedFileLoader loader;
    private IndexedFileLoader genresRelationLoader;
    private ArtistService artistService;
    private AlbumService albumService;
    private PictureService pictureService;
    private final String SEPARATOR = "|";
    private final String songsFolderPath;
    public FileSongServiceImpl(
            String path,
            String genresRelationPath,
            String songsFolderPath,
            ArtistService artistService,
            AlbumService albumService,
            PictureService pictureService
    ) {
        loader = new IndexedFileLoader(path, this.SEPARATOR);
        genresRelationLoader = new IndexedFileLoader(genresRelationPath, this.SEPARATOR);
        this.songsFolderPath = songsFolderPath;
        this.artistService = artistService;
        this.albumService = albumService;
        this.pictureService = pictureService;
    }

    public Song getById(Integer id) {
        return null;
    }

    public void delete(Song song) throws BusinessException {

    }

    public void update(Song song) throws BusinessException {

    }

    public void deleteById(Integer id) throws BusinessException {

    }

    public int add(Song song) {
        return 0;
    }

}
