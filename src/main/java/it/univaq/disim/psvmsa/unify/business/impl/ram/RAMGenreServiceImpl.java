package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.model.Genre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RAMGenreServiceImpl implements GenreService {
    private Map<Integer, Genre> genres = new HashMap<>();
    private Integer id = 0;

    @Override
    public List<Genre> getGenres() {
        return new ArrayList<>(genres.values());
    }

    public Genre getById(Integer id) {
        return genres.get(id);
    }

    public int add(Genre genre) {
        genre.setId(++id);
        genres.put(genre.getId(), genre);
        return genre.getId();
    }

    public void update(Genre genre) throws BusinessException {
        Genre existing = this.getById(genre.getId());
        if(existing == null) throw new BusinessException("Genre not found");
        genres.put(existing.getId(), genre);
    }

    public void delete(Genre genre) throws BusinessException {
        Genre existing = this.getById(genre.getId());
        if(existing == null) throw new BusinessException("Genre not found");
        genres.remove(existing.getId());
    }

}
