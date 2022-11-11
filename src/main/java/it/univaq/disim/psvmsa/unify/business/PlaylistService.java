package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.List;

public interface PlaylistService {
        List<Playlist> getPlaylistsByUser(User user) throws BusinessException;
        Playlist getById(Integer id) throws BusinessException;
        Playlist add(Playlist playlist);
        void delete(Playlist playlist) throws BusinessException;
        void update(Playlist playlist) throws BusinessException;
}
