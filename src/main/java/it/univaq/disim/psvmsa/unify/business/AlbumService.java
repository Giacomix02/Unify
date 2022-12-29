package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Artist;

import java.util.List;


public interface AlbumService {


    Album getById(Integer id) throws BusinessException;

    List<Album> getAlbums() throws BusinessException;

    void deleteById (Integer id) throws BusinessException;

    Album add(Album album);

    void update (Album album) throws BusinessException;

    void delete (Album album) throws BusinessException;

    List<Album> searchAlbumsByName(String name) throws BusinessException;

    List<Album> searchAlbumsByArtist(Artist artist) throws BusinessException;
}
