package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;

import java.util.ArrayList;
import java.util.List;

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


    public final static String SEPARATOR = "|";
    private final IndexedFileLoader loader;
    private final IndexedFileLoader loaderRelation;
    private final SongService songService;
    private final UserService userService;


    public FilePlaylistServiceImpl(
            String path,
            String relationPath,
            SongService songService,
            UserService userService
    ) {
        this.loader = new IndexedFileLoader(path, SEPARATOR, Schema.PLAYLIST_ID);
        this.loaderRelation = new IndexedFileLoader(relationPath, SEPARATOR, RelationSchema.RELATION_ID);
        this.songService = songService;
        this.userService = userService;
    }
    @Override
    public List<Playlist> getPlaylistsByUser(User user) throws BusinessException {
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
                    if (song != null){
                        playlist.addSong(song);
                    }
                } catch (SongAlreadyExistsException | BusinessException e) {
                    e.printStackTrace();
                    throw new BusinessException("Error while loading playlist");
                }
            }
            if(playlist.getSongs().size() != relationRows.size()) {
                //if some songs were deleted, update the playlist to remove those too
                addSongRelationInPlaylist(playlist);
            }
            playlists.add(playlist);
        }
        return playlists;
    }

    @Override
    public Playlist getById(Integer id) throws BusinessException {
        List<Song> songs = new ArrayList<>();
        IndexedFile.Row row = loader.getRowById(id);
        if(row == null) return null;
        List<IndexedFile.Row> relationRows = loaderRelation.loadFiltered(
                relationRow -> relationRow.getIntAt(RelationSchema.PLAYLIST_ID) == row.getIntAt(Schema.PLAYLIST_ID)
        );
        try{

            for(IndexedFile.Row r : relationRows){
                Song song = songService.getById(r.getIntAt(RelationSchema.SONG_ID));
                if(song != null){
                    songs.add(song);
                }
            }
            Playlist p = new Playlist(
                    row.getStringAt(Schema.PLAYLIST_NAME),
                    userService.getById(row.getIntAt(Schema.USER_ID)),
                    id,
                    songs
            );
            if(songs.size() != relationRows.size()) {
                //if some songs were deleted, update the playlist to remove those too
                addSongRelationInPlaylist(p);
            }
            return p;
        }catch(Exception e){
            e.printStackTrace();
            throw new BusinessException("Error while loading playlist");
        }
    }

    @Override
    public Playlist add(Playlist playlist){
        if(existsPlaylist(playlist)) return null;
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(SEPARATOR);
        int id = file.incrementId();
        playlist.setId(id);
        row.set(Schema.PLAYLIST_ID,playlist.getId())
            .set(Schema.PLAYLIST_NAME,playlist.getName())
            .set(Schema.USER_ID,playlist.getUser().getId());
        this.addSongRelationInPlaylist(playlist);
        file.appendRow(row);
        loader.save(file);
        return playlist;
    }

    @Override
    public void delete(Playlist playlist) throws BusinessException{
        if(loader.deleteRowById(playlist.getId()) == null) throw new BusinessException("Playlist not found");
        this.deleteSongRelationsInPlaylist(playlist);
    }
    public boolean existsPlaylist(Playlist p){
        if (p.getId()==null) return false;
        return (loader.getRowById(p.getId()) != null);
    }
    @Override
    public void update(Playlist playlist) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(SEPARATOR);
        row.set(Schema.PLAYLIST_ID,playlist.getId())
                .set(Schema.PLAYLIST_NAME,playlist.getName())
                .set(Schema.USER_ID,playlist.getUser().getId());
        this.deleteSongRelationsInPlaylist(playlist);
        this.addSongRelationInPlaylist(playlist);
        file.updateRow(row);
        loader.save(file);
    }

    @Override
    public Playlist upsert(Playlist playlist) throws BusinessException {
        if(existsPlaylist(playlist)){
            update(playlist);
            return playlist;
        }
        return add(playlist);
    }

    private void deleteSongRelationsInPlaylist(Playlist playlist) throws BusinessException{
        IndexedFile relationFile = loaderRelation.load();
        List<IndexedFile.Row> songs = relationFile.filterRows(
                relationRow -> relationRow.getIntAt(RelationSchema.PLAYLIST_ID) == playlist.getId()
        );
        for(IndexedFile.Row r : songs){
            relationFile.deleteRowById(r.getIntAt(RelationSchema.RELATION_ID));
        }
        loaderRelation.save(relationFile);
    }

    private void addSongRelationInPlaylist(Playlist playlist){
        IndexedFile relationFile = loaderRelation.load();
        for(Song song : playlist.getSongs()){
            IndexedFile.Row row = new IndexedFile.Row(SEPARATOR);
            int id = relationFile.incrementId();
            row.set(RelationSchema.RELATION_ID,id)
                    .set(RelationSchema.SONG_ID,song.getId())
                    .set(RelationSchema.PLAYLIST_ID,playlist.getId());
            relationFile.appendRow(row);
        }
        loaderRelation.save(relationFile);
    }
}