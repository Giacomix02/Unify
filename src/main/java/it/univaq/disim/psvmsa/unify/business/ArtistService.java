package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Artist;

public interface ArtistService {

    Artist getById(Integer id);

    void deleteById(Integer id);

    void create (Artist artist);

    void update (Artist artist);

    void delete (Artist artist);

}
