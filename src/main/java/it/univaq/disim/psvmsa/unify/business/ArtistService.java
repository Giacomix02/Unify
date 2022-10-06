package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Artist;

import java.util.List;

public interface ArtistService {

    Artist getById(Integer id);

    List<Artist> getArtists();

    void deleteById(Integer id) throws BusinessException;

    Artist add(Artist artist);

    void update (Artist artist) throws BusinessException;

    void delete (Artist artist) throws BusinessException;

    List<Artist> searchArtistsByName(String name);

}
