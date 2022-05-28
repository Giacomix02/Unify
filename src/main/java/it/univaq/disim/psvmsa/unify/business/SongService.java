package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Song;

public interface SongService {

    Song getById(Integer id);

    void delete(Song song) throws BusinessException;

    void update(Song song) throws BusinessException;

    void add(Song song);
}
