package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAllGenres();
    Genre getById(Integer id);
    int add(Genre genre);
    void delete(Genre genre) throws  BusinessException;
    void update(Genre genre) throws BusinessException;
}
