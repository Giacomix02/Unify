package it.univaq.disim.psvmsa.unify.business.impl.file;
import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Song;

import java.util.ArrayList;
import java.util.List;

public class FileAlbumServiceImpl implements AlbumService {

    private static class Schema {
        public static int ALBUM_ID = 0;
        public static int ALBUM_NAME = 1;

        public static int ARTIST_ID = 2;
    }
    private static class SongsSchema {
        public static int RELATION_ID = 0;
        public static int ALBUM_ID = 1;
        public static int SONG_ID = 2;
    }
    private final String SEPARATOR = "|";

    private final IndexedFileLoader loader;
    private final IndexedFileLoader songsRelationsLoader;
    SongService songService;
    ArtistService artistService;
    public FileAlbumServiceImpl(String path, String songRelationsPath, SongService songService, ArtistService artistService) {
        this.loader = new IndexedFileLoader(path, this.SEPARATOR);
        this.songsRelationsLoader = new IndexedFileLoader(songRelationsPath, this.SEPARATOR);
        this.songService = songService;
        this.artistService = artistService;
    }

    @Override
    public Album getById(Integer id) throws BusinessException {
        IndexedFile.Row row = loader.getRowById(id);
        if (row == null) return null;
        List<Song> songs = getSongsRelations(id);
        return new Album(
                row.getStringAt(Schema.ALBUM_NAME),
                songs,
                artistService.getById(row.getIntAt(Schema.ARTIST_ID)),
                row.getIntAt(Schema.ALBUM_ID)
        );
    }

    @Override
    public List<Album> getAlbums() throws BusinessException {
        List<Album> albums = new ArrayList<>();
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.getRows();
        for (IndexedFile.Row row : rows) {
            List<Song> songs = getSongsRelations(row.getIntAt(Schema.ALBUM_ID));
            albums.add(new Album(
                    row.getStringAt(Schema.ALBUM_NAME),
                    songs,
                    artistService.getById(row.getIntAt(Schema.ARTIST_ID)),
                    row.getIntAt(Schema.ALBUM_ID)
            ));
        }
        return albums;
    }

    @Override
    public void deleteById(Integer id) throws BusinessException {
        if(loader.deleteRowById(id) == null) throw new BusinessException("Album not found");
    }

    @Override
    public Album add(Album album) {

        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        int id = file.incrementId();
        album.setId(id);
        row.set(Schema.ALBUM_ID, album.getId())
                .set(Schema.ALBUM_NAME, album.getName())
                .set(Schema.ARTIST_ID, album.getArtist().getId());
        file.appendRow(row);
        loader.save(file);
        addSongsRelations(id, album.getSongs());
        artistService.add(album.getArtist());
        return album;
    }

    @Override
    public void update(Album album) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        row.set(Schema.ALBUM_ID, album.getId())
                .set(Schema.ALBUM_NAME, album.getName())
                .set(Schema.ARTIST_ID, album.getArtist().getId());
        file.updateRow(row);
        loader.save(file);
        deleteSongsRelations(album.getId());
        addSongsRelations(album.getId(), album.getSongs());
    }

    @Override
    public void delete(Album album) throws BusinessException {
        deleteById(album.getId());
        deleteSongsRelations(album.getId());
    }
    public List<Song> getSongsRelations(Integer albumId) throws BusinessException{
        List<Song> songs = new ArrayList<>();
        List<IndexedFile.Row> rows = loader.loadFiltered((r) -> r.getIntAt(Schema.ALBUM_ID) == albumId);
        for (IndexedFile.Row row : rows) {
            songs.add(songService.getById(row.getIntAt(SongsSchema.SONG_ID)));
        }
        return songs;
    }
    public void deleteSongsRelations(Integer albumId) throws BusinessException{
        IndexedFile file = songsRelationsLoader.load();
        List<IndexedFile.Row> rows = file.filterRows((r) -> r.getIntAt(SongsSchema.ALBUM_ID) == albumId);
        for (IndexedFile.Row row : rows) {
            file.deleteRowById(row.getIntAt(SongsSchema.RELATION_ID));
        }
        songsRelationsLoader.save(file);
    }
    public void addSongsRelations(Integer albumId, List<Song> songs){
        IndexedFile file = songsRelationsLoader.load();
        for (Song song : songs) {
            try{
                this.songService.add(song);
            }catch(Exception ignored){}
            IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
            int id = file.incrementId();
            row.set(SongsSchema.RELATION_ID, id)
                    .set(SongsSchema.ALBUM_ID, albumId)
                    .set(SongsSchema.SONG_ID, song.getId());
            file.appendRow(row);
        }
        songsRelationsLoader.save(file);
    }
    @Override
    public List<Album> searchAlbumsByName(String name) throws BusinessException {
        List<IndexedFile.Row> rows = loader.loadFiltered(r -> r.getStringAt(Schema.ALBUM_NAME).toLowerCase().contains(name.toLowerCase()));
        List<Album> albums = new ArrayList<>();
        for (IndexedFile.Row row : rows) {
            Album album = this.getById(row.getIntAt(Schema.ALBUM_ID));
            albums.add(album);
        }
        return albums;
    }

    @Override
    public List<Album> searchAlbumsByArtist(Artist artist) throws BusinessException {
        List<IndexedFile.Row> rows = loader.loadFiltered(r -> r.getIntAt(Schema.ARTIST_ID) == artist.getId());
        List<Album> albums = new ArrayList<>();
        for (IndexedFile.Row row : rows) {
            Album album = this.getById(row.getIntAt(Schema.ALBUM_ID));
            albums.add(album);
        }
        return albums;
    }
}
