package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.function.Predicate;

public class FileSongServiceImpl implements SongService {
    private static class Schema {
        public static int SONG_ID = 0;
        public static int SONG_NAME = 1;
        public static int ARTIST_ID = 2;
        public static int ALBUM_ID = 3;
        public static int LYRICS = 4;
        public static int PICTURE_ID = 5;
    }

    private static class RelationSchema {
        public static int RELATION_ID = 0;
        public static int SONG_ID = 1;
        public static int GENRE_ID = 2;
    }

    private final IndexedFileLoader loader;
    private final IndexedFileLoader genresRelationLoader;
    private final ArtistService artistService;
    private final AlbumService albumService;
    private final PictureService pictureService;
    private final GenreService genreService;
    private final String SEPARATOR = "|";
    private final String songsFolderPath;

    public FileSongServiceImpl(
            String path,
            String genresRelationPath,
            String songsFolderPath,
            ArtistService artistService,
            AlbumService albumService,
            PictureService pictureService,
            GenreService genreService
    ) {
        this.loader = new IndexedFileLoader(path, this.SEPARATOR, Schema.SONG_ID);
        this.genresRelationLoader = new IndexedFileLoader(genresRelationPath, this.SEPARATOR, RelationSchema.RELATION_ID);
        this.songsFolderPath = songsFolderPath;
        this.artistService = artistService;
        this.albumService = albumService;
        this.pictureService = pictureService;
        this.genreService = genreService;
        ensureSongsFolderExists();
    }


    public Song getById(Integer id) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile relationFile = genresRelationLoader.load();
        IndexedFile.Row row = file.findRowById(id);

        List<IndexedFile.Row> relationRows = relationFile.filterRows(
                r -> r.getIntAt(RelationSchema.SONG_ID) == id
        );


        List<Genre> genres = new ArrayList<>();
        for (IndexedFile.Row relationRow : relationRows) {
            Genre genre = genreService.getById(relationRow.getIntAt(RelationSchema.GENRE_ID));
            genres.add(genre);
        }

        try {
            return new Song(
                    row.getStringAt(Schema.SONG_NAME),
                    albumService.getById(row.getIntAt(Schema.ALBUM_ID)),
                    artistService.getById(row.getIntAt(Schema.ARTIST_ID)),
                    row.getStringAt(Schema.LYRICS),
                    pictureService.getById(row.getIntAt(Schema.PICTURE_ID)),
                    genres,
                    getSongStream(row.getIntAt(Schema.SONG_ID)).readAllBytes(),
                    row.getIntAt(Schema.SONG_ID)
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException("Error while reading song content");
        }
    }


    public void delete(Song song) throws BusinessException {
        IndexedFile file = loader.load();
        file.deleteRowById(song.getId());
        this.deleteRelations(song);
        loader.save(file);
    }

    public void update(Song song) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(SEPARATOR);
        row.set(Schema.SONG_ID, song.getId())
                .set(Schema.SONG_NAME, song.getName())
                .set(Schema.ARTIST_ID, song.getArtist().getId())
                .set(Schema.ALBUM_ID, song.getAlbum().getId())
                .set(Schema.LYRICS, song.getLyrics())
                .set(Schema.PICTURE_ID, song.getPicture().getId());
        this.deleteRelations(song);
        this.addRelations(song);
        file.updateRow(row);
        loader.save(file);
    }

    public void deleteById(Integer id) throws BusinessException {
        IndexedFile file = loader.load();
        file.deleteRowById(id);
        loader.save(file);
    }

    public Song add(Song song) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(SEPARATOR);
        song.setId(file.incrementId());
        row.set(Schema.SONG_ID, song.getId())
                .set(Schema.SONG_NAME, song.getName())
                .set(Schema.ARTIST_ID, song.getArtist().getId())
                .set(Schema.ALBUM_ID, song.getAlbum().getId())
                .set(Schema.LYRICS, song.getLyrics())
                .set(Schema.PICTURE_ID, song.getPicture().getId());

        this.saveSongToFile(song);
        file.appendRow(row);
        this.addRelations(song);
        loader.save(file);
        return song;
    }

    private void deleteRelations(Song song) throws BusinessException {
        IndexedFile relationFile = genresRelationLoader.load();
        List<IndexedFile.Row> rows = relationFile.filterRows(
                r -> r.getIntAt(RelationSchema.SONG_ID) == song.getId()
        );
        for (IndexedFile.Row row : rows) {
            relationFile.deleteRowById(row.getIntAt(RelationSchema.SONG_ID));
        }
        genresRelationLoader.save(relationFile);
    }

    private void addRelations(Song song) {
        IndexedFile relationFile = genresRelationLoader.load();
        for (Genre genre : song.getGenres()) {
            IndexedFile.Row row = new IndexedFile.Row(SEPARATOR);
            int id = relationFile.incrementId();
            row.set(RelationSchema.RELATION_ID, id)
                    .set(RelationSchema.SONG_ID, song.getId())
                    .set(RelationSchema.GENRE_ID, genre.getId());
            relationFile.appendRow(row);
        }
        genresRelationLoader.save(relationFile);
    }


    public List<Song> getAllSongs() throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile relationFile = genresRelationLoader.load();

        List<IndexedFile.Row> rows = file.getRows();
        List<Song> songs = new ArrayList<>();

        for (IndexedFile.Row row : rows) {

            List<Genre> genres = new ArrayList<>();

            List<IndexedFile.Row> relationRows = relationFile.filterRows(
                    r -> r.getIntAt(RelationSchema.SONG_ID) == row.getIntAt(Schema.SONG_ID)
            );

            for (IndexedFile.Row relationRow : relationRows) {
                Genre genre = genreService.getById(relationRow.getIntAt(RelationSchema.GENRE_ID));
                genres.add(genre);
            }

            try{
                Song song = new Song(
                        row.getStringAt(Schema.SONG_NAME),
                        albumService.getById(row.getIntAt(Schema.ALBUM_ID)),
                        artistService.getById(row.getIntAt(Schema.ARTIST_ID)),
                        row.getStringAt(Schema.LYRICS),
                        pictureService.getById(row.getIntAt(Schema.PICTURE_ID)),
                        genres,
                        getSongStream(row.getIntAt(Schema.SONG_ID)).readAllBytes(),
                        row.getIntAt(Schema.SONG_ID)
                );
                songs.add(song);
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException("Error while loading song content");
            }
        }
        return songs;
    }


    private void saveSongToFile(Song song){
        File fileToSave = new File(this.songsFolderPath + song.getId() + ".mp3");
            try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                outputStream.write(song.getContent());
            }catch (IOException e) {
                //TODO maybe relaunch error
                e.printStackTrace();
            }
    }

    private InputStream getSongStream(Integer id){
        try{
            File file = new File(this.songsFolderPath + id + ".mp3");
            return new FileInputStream(file);
        }catch(Exception e){
            e.printStackTrace();
            //TODO maybe relaunch error
            return null;
        }
    }

    private void ensureSongsFolderExists(){
        File folder = new File(this.songsFolderPath);
        if(!folder.exists()) folder.mkdirs();
    }
}
