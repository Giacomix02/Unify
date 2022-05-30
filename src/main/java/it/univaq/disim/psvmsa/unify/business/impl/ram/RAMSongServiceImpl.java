package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.model.Song;

import java.util.ArrayList;
import java.util.List;

public class RAMSongServiceImpl implements SongService {

    static List<Song> songs = new ArrayList<Song>();
    static Integer id = 0;

    @Override
    public Song getById(Integer id) {
        for (Song song : songs) {
            if (song.getId().equals(id)) {
                return song;
            }
        }
        return null;
    }

    @Override
    public void add(Song song) {
        song.setId(++id);
        songs.add(song);
    }

    @Override
    public void delete(Song song) throws BusinessException {
        if (songs.contains(song)) {
            songs.remove(song);
        } else {
            throw new BusinessException("Song not found");
        }
    }

    @Override
    public void update(Song song) throws BusinessException {
        int index = findIndexById(song.getId());
        if (index < 0) throw new BusinessException("Song not found");
        songs.set(index, song);
    }

    private int findIndexById(Integer id) {
        for (int i = 0; i < songs.size(); i++) {
            if (songs.get(i).getId().equals(id)) return i;
        }
        return -1;
    }
}
