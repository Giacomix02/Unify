package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class RAMPlaylistServiceImpl implements PlaylistService {
    private Map<Integer, Playlist> playlists = new HashMap<>();
    private Integer id = 0;

    public List<Playlist> getPlaylistsByUser(User user) {
        return playlists
                .values()
                .stream()
                .filter(playlist -> playlist.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public Playlist getById(Integer id) {
        return playlists.get(id);
    }

    public Playlist add(Playlist playlist) {
        playlist.setId(++id);
        playlists.put(playlist.getId(), playlist);
        return playlist;
    }
    public void update(Playlist playlist) throws BusinessException {
        Playlist existing = this.getById(playlist.getId());
        if(existing == null) throw new BusinessException("Playlist not found");
        playlists.put(existing.getId(), playlist);
    }

    public void delete(Playlist playlist) throws BusinessException{
        Playlist existing = this.getById(playlist.getId());
        if(existing == null) throw new BusinessException("Playlist not found");
        playlists.remove(existing.getId());
    }
}
