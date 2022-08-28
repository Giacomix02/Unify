package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Artist;

public interface ArtistService {

    Artist getById(Integer id);

    void deleteById(Integer id) throws BusinessException;

    int add(Artist artist);

    void update (Artist artist) throws BusinessException;

    void delete (Artist artist) throws BusinessException;

}
