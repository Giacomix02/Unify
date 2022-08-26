package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class RAMGenreServiceImpl implements GenreService {
    private List<Genre> genres = new ArrayList<>();
    private Integer id = 0;

    @Override
    public List<Genre> getAllGenres() {
        return genres;
    }

    @Override
    public Genre getById(Integer id) {
        for (Genre genre : genres) {
            if (genre.getId().equals(id)) {
                return genre;
            }
        }
        return null;
    }

    @Override
    public void add(Genre genre) {
        genre.setId(++id);
        genres.add(genre);
    }

    @Override
    public void delete(Genre genre) throws BusinessException {
        int index = findIndexById(genre.getId());
        if (index < 0) throw new BusinessException("Genre not found");
        genres.remove(index);
    }

    @Override
    public void update(Genre genre) throws BusinessException {
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
