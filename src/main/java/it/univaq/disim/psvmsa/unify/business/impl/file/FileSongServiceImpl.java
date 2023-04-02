package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.Artist;
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
        public static int LYRICS = 3;
        public static int PICTURE_ID = 4;

        public static int GENRE_ID = 5;
    }

    private final IndexedFileLoader loader;
    private final ArtistService artistService;
    private final PictureService pictureService;
    private final GenreService genreService;
    private final String SEPARATOR = "|";
    private final String songsFolderPath;

    public FileSongServiceImpl(
            String path,
            String songsFolderPath,
            ArtistService artistService,
            PictureService pictureService,
            GenreService genreService
    ) {
        this.loader = new IndexedFileLoader(path, this.SEPARATOR, Schema.SONG_ID);
        this.songsFolderPath = songsFolderPath;
        this.artistService = artistService;
        this.pictureService = pictureService;
        this.genreService = genreService;
        ensureSongsFolderExists();
    }

    private boolean existsSong(Song song){
        if(song.getId() == null) return false;
        IndexedFile file = loader.load();
        return file.findRowById(song.getId()) != null;
    }
    public Song getById(Integer id) throws BusinessException {
        IndexedFile.Row row = loader.getRowById(id);
        try {
            return new Song(
                    row.getStringAt(Schema.SONG_NAME),
                    artistService.getById(row.getIntAt(Schema.ARTIST_ID)),
                    row.getStringAt(Schema.LYRICS),
                    pictureService.getById(row.getIntAt(Schema.PICTURE_ID)),
                    genreService.getById(row.getIntAt(Schema.GENRE_ID)),
                    getSongStream(row.getIntAt(Schema.SONG_ID)),
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
        loader.save(file);
    }

    public void update(Song song) throws BusinessException {
        try {
            pictureService.add(song.getPicture());
            artistService.add(song.getArtist());
        } catch (Exception ignored){}

        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(SEPARATOR);
        row.set(Schema.SONG_ID, song.getId())
                .set(Schema.SONG_NAME, song.getName())
                .set(Schema.ARTIST_ID, song.getArtist().getId())
                .set(Schema.LYRICS, song.getLyrics())
                .set(Schema.PICTURE_ID, song.getPicture().getId())
                .set(Schema.GENRE_ID, song.getGenre().getId());
        file.updateRow(row);
        loader.save(file);
    }

    public void deleteById(Integer id) throws BusinessException {
        if(loader.deleteRowById(id) == null) throw new BusinessException("Song not found");
    }

    public Song add(Song song) throws BusinessException{
        if(this.existsSong(song)) return null;
        Picture pic = pictureService.upsert(song.getPicture());
        Artist art = artistService.upsert(song.getArtist());
        song.setArtist(art);
        song.setPicture(pic);
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(SEPARATOR);
        song.setId(file.incrementId());
        row.set(Schema.SONG_ID, song.getId())
                .set(Schema.SONG_NAME, song.getName())
                .set(Schema.ARTIST_ID, art.getId())
                .set(Schema.LYRICS, song.getLyrics())
                .set(Schema.PICTURE_ID, pic.getId())
                .set(Schema.GENRE_ID, song.getGenre().getId());
        this.saveSongToFile(song);
        file.appendRow(row);
        loader.save(file);
        return song;
    }

    @Override
    public Song upsert(Song song) throws BusinessException {
        if(this.existsSong(song)){
            this.update(song);
            return song;
        }
        return this.add(song);
    }



    public List<Song> getAllSongs() throws BusinessException {
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.getRows();
        List<Song> songs = new ArrayList<>();
        for (IndexedFile.Row row : rows) {
                Song song = new Song(
                        row.getStringAt(Schema.SONG_NAME),
                        artistService.getById(row.getIntAt(Schema.ARTIST_ID)),
                        row.getStringAt(Schema.LYRICS),
                        pictureService.getById(row.getIntAt(Schema.PICTURE_ID)),
                        genreService.getById(row.getIntAt(Schema.GENRE_ID)),
                        getSongStream(row.getIntAt(Schema.SONG_ID)),
                        row.getIntAt(Schema.SONG_ID)
                );
                songs.add(song);
        }
        return songs;
    }


    private void saveSongToFile(Song song){
        File fileToSave = new File(this.songsFolderPath + song.getId() + ".mp3");
            try (FileOutputStream outputStream = new FileOutputStream(fileToSave)) {
                outputStream.write(song.toStream().readAllBytes());
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

    public List<Song> searchByName(String name) throws BusinessException{
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.filterRows(
                r -> r.getStringAt(Schema.SONG_NAME).toLowerCase().contains(name.toLowerCase())
        );
        List<Song> songs = new ArrayList<>();
        for (IndexedFile.Row row : rows) {
            Song song = this.getById(row.getIntAt(Schema.SONG_ID));
            songs.add(song);
        }
        return songs;
    }

    public List<Song> searchByArtist(Artist artist) throws BusinessException {
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.filterRows(
                r -> r.getIntAt(Schema.ARTIST_ID) == artist.getId()
        );
        List<Song> songs = new ArrayList<>();
        for (IndexedFile.Row row : rows) {
            Song song = this.getById(row.getIntAt(Schema.SONG_ID));
            songs.add(song);
        }
        return songs;
    }

    @Override
    public List<Song> searchByGenre(Genre genre) throws BusinessException {
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.filterRows(
                r -> r.getIntAt(Schema.GENRE_ID) == genre.getId()
        );
        List<Song> songs = new ArrayList<>();
        for (IndexedFile.Row row : rows) {
            Song song = this.getById(row.getIntAt(Schema.SONG_ID));
            songs.add(song);
        }
        return songs;
    }
}
