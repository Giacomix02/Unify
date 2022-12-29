package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Song;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RAMSongServiceImpl implements SongService {

    private Map<Integer, Song> songs = new HashMap<>();
    private Integer id = 0;

    @Override
    public Song getById(Integer id) {
        return this.songs.get(id);
    }

    @Override
    public Song add(Song song) {
        song.setId(++id);
        this.songs.put(song.getId(), song);
        return song;
    }

    @Override
    public void update(Song song) throws BusinessException {
        Song existing = getById(song.getId());
        if(existing == null) throw new BusinessException("Song not found");
        songs.put(song.getId(), song);
    }

    @Override
    public void delete(Song song) throws BusinessException {
        deleteById(song.getId());
    }

    @Override
    public void deleteById(Integer id) throws BusinessException {
        Song existing = this.songs.remove(id);
        if(existing == null) throw new BusinessException("Song not found");
    }

    public List<Song> getAllSongs(){
        return new ArrayList<>(songs.values());
    }

    public List<Song> searchByName(String name) {
        ArrayList<Song> songs = new ArrayList<>();
        for(Song song : this.songs.values()) {
            if(song.getName().contains(name.toLowerCase())) {
                songs.add(song);
            }
        }
        return songs;
    }

    @Override
    public List<Song> searchByArtist(Artist artist) {
ArrayList<Song> songs = new ArrayList<>();
        for(Song song : this.songs.values()) {
            if(song.getArtist().equals(artist)) {
                songs.add(song);
            }
        }
        return songs;

    }

}
