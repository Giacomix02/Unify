package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Song;

public interface SongService {

    Song getById(Integer id);

    void delete(Song song);

    void update(Song song);

    void add(Song song);
}
