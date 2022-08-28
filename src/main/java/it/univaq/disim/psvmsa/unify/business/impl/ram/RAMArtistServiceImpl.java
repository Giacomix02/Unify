package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.model.Artist;

import java.util.HashMap;
import java.util.Map;

public class RAMArtistServiceImpl implements ArtistService {

    private Map<Integer,Artist> artists = new HashMap<>();

    private Integer id = 0;

    @Override
    public Artist getById(Integer id) {
        return this.artists.get(id);
    }

    @Override
    public int add(Artist artist) {
        artist.setId(++id);
        artists.put(artist.getId(),artist);
        return artist.getId();
    }

    @Override
    public void update(Artist artist) throws BusinessException {

        Artist existing = getById(artist.getId());
        if(existing == null) throw new BusinessException("Artist not found");
        artists.put(artist.getId(),artist);
    }

    @Override
    public void delete(Artist artist) throws BusinessException {
        deleteById(artist.getId());
    }

    @Override
    public void deleteById(Integer id) throws BusinessException{
        Artist existing = this.artists.remove(id);
        if(existing==null) throw new BusinessException("Artist not found");
    }
}
