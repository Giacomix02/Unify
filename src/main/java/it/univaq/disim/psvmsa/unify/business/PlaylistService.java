package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.List;

public interface PlaylistService {
        List<Playlist> getPlaylistsByUser(User user);
        Playlist getById(Integer id);
        Playlist add(Playlist playlist);
        Playlist delete(Playlist playlist) throws BusinessException;
        Playlist update(Playlist playlist) throws BusinessException;
}
