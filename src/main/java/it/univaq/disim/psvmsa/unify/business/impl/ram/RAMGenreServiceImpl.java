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

    @Override
    public Genre upsert(Genre genre) throws BusinessException {
        if(genre.getId()==null || getById(genre.getId()) == null) {
            return add(genre);
        } else {
            update(genre);
            return genre;
        }
    }

    public Genre add(Genre genre)  {
        List<Genre> g = searchByName(genre.getName());
        if(!g.isEmpty()) return null;
        genre.setId(++id);
        this.genres.put(genre.getId(), genre);
        return genre;
    }

    public void update(Genre genre) throws BusinessException {
        Genre existing = this.getById(genre.getId());
        if(existing == null) throw new BusinessException("Genre not found");
        genres.put(existing.getId(), genre);
    }
    public boolean existGenreName(String name) {
        for(Genre g : genres.values()){
            if(g.getName().toLowerCase().equals(name.toLowerCase())) return true;
        }
        return false;
    }

    public void delete(Genre genre) throws BusinessException {
        Genre existing = this.getById(genre.getId());
        if(existing == null) throw new BusinessException("Genre not found");
        genres.remove(existing.getId());
    }
    public List<Genre> searchByName(String name) {
        ArrayList<Genre> genres = new ArrayList<>();
        for (Genre genre : this.genres.values()) {
            if (genre.getName().contains(name)) {
                genres.add(genre);
            }
        }
        return genres;
    }

}
