package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getGenres();
    Genre getById(Integer id);
    Genre add(Genre genre);
    void delete(Genre genre) throws BusinessException;
    void update(Genre genre) throws BusinessException;
    List<Genre> searchByName(String name);
}

//TODO when a genre is deleted, all the songs with that genre must be updated