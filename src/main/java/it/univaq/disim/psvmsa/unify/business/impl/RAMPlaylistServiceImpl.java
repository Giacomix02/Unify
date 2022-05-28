package it.univaq.disim.psvmsa.unify.business.impl;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.ArrayList;
import java.util.List;


public class RAMPlaylistServiceImpl implements PlaylistService {
    static List<Playlist> playlists = new ArrayList<>();
    static Integer id = 0;

    @Override
    public List<Playlist> getPlaylistsByUser(User user) {
        List<Playlist> userPlaylists = new ArrayList<>();
        for (Playlist p : playlists) {
            if (p.getUser().getId().equals(user.getId())) {
                userPlaylists.add(p);
            }
        }
        return userPlaylists;
    }

    @Override
    public Playlist getPlaylistById(Integer id) {
        for (Playlist playlist : playlists) {
            if (playlist.getId().equals(id)) {
                return playlist;
            }
        }
        return null;
    }

    @Override
    public int createPlaylist(Playlist playlist) {
        playlist.setId(++id);
        playlists.add(playlist);
        return 0;
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        int index = findIndexById(playlist.getId());
        if (index < 0) return;
        playlists.remove(index);
    }

    @Override
    public void updatePlaylist(Playlist playlist) throws BusinessException {
        int index = findIndexById(playlist.getId());
        if (index < 0) throw new BusinessException("Playlist not found");
        playlists.set(index, playlist);
    }

    private int findIndexById(Integer id) {
        for (int i = 0; i < playlists.size(); i++) {
            if (playlists.get(i).getId().equals(id)) return i;
        }
        return -1;
    }
}
