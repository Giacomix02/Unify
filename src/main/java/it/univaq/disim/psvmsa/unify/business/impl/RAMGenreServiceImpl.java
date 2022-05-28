package it.univaq.disim.psvmsa.unify.business.impl;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class RAMGenreServiceImpl implements GenreService {
    static List<Genre> genres = new ArrayList<>();
    static Integer id = 0;

    @Override
    public List<Genre> getAllGenres() {
        return genres;
    }

    @Override
    public Genre getGenreById(Integer id) {
        for (Genre g : genres) {
            if (g.getId().equals(id)) {
                return g;
            }
        }
        return null;
    }

    @Override
    public void createGenre(Genre genre) {
        genre.setId(++id);
        genres.add(genre);
    }

    @Override
    public boolean deleteGenre(Genre genre) {
        return false;
    }

    @Override
    public void updateGenre(Genre genre) throws BusinessException {
        int index = findIndexById(genre.getId());
        if(index < 0) throw new BusinessException("Genre not found");
        genres.set(index, genre);
    }
    private int findIndexById(Integer id) {
        for (int i = 0; i < genres.size(); i++) {
            if (genres.get(i).getId().equals(id)) return i;
        }
        return -1;
    }
}
