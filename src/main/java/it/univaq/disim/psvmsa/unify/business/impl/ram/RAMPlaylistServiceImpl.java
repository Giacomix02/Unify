package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class RAMPlaylistServiceImpl implements PlaylistService {
    private Map<Integer, Playlist> playlists = new HashMap<>();
    private SongService songService;

    public RAMPlaylistServiceImpl(SongService songService) {
        this.songService = songService;
    }

    private Integer id = 0;

    public List<Playlist> getPlaylistsByUser(User user) throws BusinessException {
        //get updated songs
        List<Playlist> p = playlists
                .values()
                .stream()
                .filter(playlist -> playlist.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
        List<Playlist> updated = new LinkedList<>();
        for (Playlist playlist : p) {
            updated.add(getById(playlist.getId()));
        }
        return updated;
    }

    public Playlist getById(Integer id) throws BusinessException {
        Playlist p = playlists.get(id);
        if (p == null) return null;
        List<Song> songs = new LinkedList<>();
        for (Song s : p.getSongs()) {
            songs.add(songService.getById(s.getId()));
        }
        p.setSongs(songs);
        return p;
    }

    public Playlist add(Playlist playlist) {
        playlist.setId(++id);
        playlists.put(playlist.getId(), playlist);
        return playlist;
    }

    public void update(Playlist playlist) throws BusinessException {
        Playlist existing = this.getById(playlist.getId());
        if (existing == null) throw new BusinessException("Playlist not found");
        playlists.put(existing.getId(), playlist);
    }

    @Override
    public Playlist upsert(Playlist playlist) throws BusinessException {
        if (playlist.getId() == null || getById(playlist.getId()) == null) {
            return add(playlist);
        } else {
            update(playlist);
            return playlist;
        }
    }

    public void delete(Playlist playlist) throws BusinessException {
        Playlist existing = this.getById(playlist.getId());
        if (existing == null) throw new BusinessException("Playlist not found");
        playlists.remove(existing.getId());
    }
}
