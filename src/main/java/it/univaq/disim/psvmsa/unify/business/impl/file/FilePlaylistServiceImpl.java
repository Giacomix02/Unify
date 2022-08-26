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
    public Playlist getById(Integer id) {
        return null;
    }

    @Override
    public int add(Playlist playlist) {
        return 0;
    }

    @Override
    public void delete(Playlist playlist) {

    }

    @Override
    public void update(Playlist playlist) throws BusinessException {

    }
}
