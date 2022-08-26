package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.List;

public interface PlaylistService {
        List<Playlist> getPlaylistsByUser(User user);
        Playlist getById(Integer id);
        int add(Playlist playlist);
        void delete(Playlist playlist);
        void update(Playlist playlist) throws BusinessException;
}
