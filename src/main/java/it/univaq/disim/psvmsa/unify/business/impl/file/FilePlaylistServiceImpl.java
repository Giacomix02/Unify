package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FilePlaylistServiceImpl implements PlaylistService {


    private static class Schema {

        public static int PLAYLIST_ID = 0;
        public static int PLAYLIST_NAME = 1;
        public static int USER_ID = 2;

    }

    public static class RelationSchema {

        public static int RELATION_ID = 0;
        public static int SONG_ID = 1;
        public static int PLAYLIST_ID = 2;
    }


    public static String SEPARATOR = "|";

    private IndexedFileLoader loader;
    private IndexedFileLoader loaderRelation;
    private SongService songService;

    private UserService userService;


    public FilePlaylistServiceImpl(String path, String relationPath, SongService songService) {
        this.loader = new IndexedFileLoader(path, SEPARATOR);
        this.loaderRelation = new IndexedFileLoader(relationPath, SEPARATOR);
        this.songService = songService;
    }


    @Override
    public List<Playlist> getPlaylistsByUser(User user) {

        List<Playlist> playlists = new ArrayList<>();

        IndexedFile relationFile = loaderRelation.load();
        IndexedFile playlistFile = loader.load();


        List<IndexedFile.Row> rows = playlistFile.filterRows(row -> row.getIntAt(Schema.USER_ID) == user.getId());


        for (IndexedFile.Row row : rows) {
            Playlist playlist = new Playlist(
                    row.getStringAt(Schema.PLAYLIST_NAME),
                    user,
                    row.getIntAt(Schema.PLAYLIST_ID)
            );

            List<IndexedFile.Row> relationRows = relationFile.filterRows(
                    relationRow -> relationRow.getIntAt(RelationSchema.PLAYLIST_ID) == playlist.getId()
            );

            for (IndexedFile.Row relationRow : relationRows) {
                try {
                    Song song = songService.getById(relationRow.getIntAt(RelationSchema.SONG_ID));
                    playlist.addSong(song);
                } catch (SongAlreadyExistsException e) {
                    System.out.println(e.getMessage());
                }
            }

            playlists.add(playlist);

        }

        return playlists;

    }

    @Override
    public Playlist getById(Integer id) {
        IndexedFile file = loader.load();
        IndexedFile relationFile = loaderRelation.load();

        List<Song> songs = new ArrayList<>();

        IndexedFile.Row row = file.findRowById(id);

        List<IndexedFile.Row> relationRows = relationFile.filterRows(
                relationRow -> relationRow.getIntAt(RelationSchema.PLAYLIST_ID) == row.getIntAt(Schema.PLAYLIST_ID)
        );

        for(IndexedFile.Row r : relationRows){
            songs.add( songService.getById(r.getIntAt(RelationSchema.SONG_ID)));
        }


        Playlist playlist = new Playlist(row.getStringAt(Schema.PLAYLIST_NAME), userService.getById(row.getIntAt(Schema.USER_ID)),id,songs);


        return playlist;
    }

    @Override
    public Playlist add(Playlist playlist) {
        IndexedFile file = loader.load();

        IndexedFile.Row row = new IndexedFile.Row(SEPARATOR);

        row.set(Schema.PLAYLIST_ID,playlist.getId())
            .set(Schema.PLAYLIST_NAME,playlist.getName())
            .set(Schema.USER_ID,playlist.getUser().getId());

        file.appendRow(row);
        loader.save(file);

        return playlist;
    }

    @Override
    public Playlist delete(Playlist playlist) throws BusinessException{
        IndexedFile file = loader.load();

        file.deleteRowById(playlist.getId());

        loader.save(file);

        return playlist;
    }

    @Override
    public Playlist update(Playlist playlist) throws BusinessException {
        IndexedFile file = loader.load();

        IndexedFile.Row row = new IndexedFile.Row(SEPARATOR);
        row.set(Schema.PLAYLIST_ID,playlist.getId())
                .set(Schema.PLAYLIST_NAME,playlist.getName())
                .set(Schema.USER_ID,playlist.getUser().getId());

        file.updateRow(row);
        loader.save(file);

        return playlist;
    }
}