package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Album;

import java.util.List;


public interface AlbumService {


    Album getById(Integer id);

    List<Album> getAlbums();

    void deleteById (Integer id) throws BusinessException;

    Album add(Album album);

    void update (Album album) throws BusinessException;

    void delete (Album album) throws BusinessException;

    List<Album> searchAlbumsByName(String name);
}
