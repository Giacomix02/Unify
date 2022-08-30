package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.model.*;

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
        return null;
    }

    @Override
    public int add(Playlist playlist) {
        return 0;
    }

    @Override
    public void delete(Playlist playlist) {

    }

    @Override
    public void update(Playlist playlist) throws BusinessException {

    }
}
