package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAllGenres();
    Genre getGenreById(Integer id);
    void createGenre(Genre genre);
    boolean deleteGenre(Genre genre);
    void updateGenre(Genre genre) throws BusinessException;
}
