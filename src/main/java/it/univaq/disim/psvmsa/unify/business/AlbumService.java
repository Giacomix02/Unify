package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Album;


public interface AlbumService {


    Album getById(Integer id);

    void deleteById (Integer id) throws BusinessException;

    int add(Album album);

    void update (Album album) throws BusinessException;

    void delete (Album album) throws BusinessException;

}
