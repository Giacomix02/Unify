package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.business.impl.file.IndexedFile;
import it.univaq.disim.psvmsa.unify.model.Song;

import java.util.List;
import java.util.function.Predicate;

public interface SongService {

    Song getById(Integer id) throws BusinessException;

    void delete(Song song) throws BusinessException;

    void update(Song song) throws BusinessException;

    void deleteById(Integer id) throws BusinessException;

    Song add(Song song);

    List<Song> getAllSongs() throws BusinessException;

    List<Song> searchByName(String name) throws BusinessException;

}
