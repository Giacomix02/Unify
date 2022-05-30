package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.List;

public class FilePlaylistServiceImpl implements PlaylistService {
    @Override
    public List<Playlist> getPlaylistsByUser(User user) {
        return null;
    }

    @Override
    public Playlist getPlaylistById(Integer id) {
        return null;
    }

    @Override
    public int createPlaylist(Playlist playlist) {
        return 0;
    }

    @Override
    public void deletePlaylist(Playlist playlist) {

    }

    @Override
    public void updatePlaylist(Playlist playlist) throws BusinessException {

    }
}
