package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class RAMArtistServiceImpl implements ArtistService {

    private List<Artist> artists = new ArrayList<>();

    private Integer id = 0;

    @Override
    public Artist getById(Integer id) {
        for (Artist artist : artists) {
            if (artist.getId().equals(id)) {
                return artist;
            }
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        for (Artist artist : artists) {
            if(artist.getId().equals(id)) {
                artists.remove(artist);
            }
        }
    }

    @Override
    public void add(Artist artist) {
        artist.setId(++id);
        artists.add(artist);
    }

    @Override
    public void delete(Artist artist) throws BusinessException {
        if(artists.contains(artist)) {
            artists.remove(artist);
        } else {
            throw new BusinessException("Artist not found");
        }
    }

    @Override
    public void update(Artist artist) throws BusinessException {
        int index = findIndexById(artist.getId());
        if(index < 0) throw new BusinessException("Artist not found");
        artists.set(index, artist);
    }

    private int findIndexById(Integer id) {
        for(int i = 0; i < artists.size(); i++) {
            if (artists.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
