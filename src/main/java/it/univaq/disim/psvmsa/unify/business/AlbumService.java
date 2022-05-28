package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Album;

public interface AlbumService {

    Album getById(Integer id);

    void deleteById (Integer id);

    void create (Album album);

    void update (Album album);

    void delete (Album album);

}
