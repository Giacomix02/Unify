package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Genre;

import java.util.List;


public interface AlbumService {


    Album getById(Integer id) throws BusinessException;

    List<Album> getAlbums() throws BusinessException;

    void deleteById (Integer id) throws BusinessException;

    Album add(Album album) throws BusinessException;


    void update (Album album) throws BusinessException;

    void delete (Album album) throws BusinessException;

    Album upsert (Album album) throws BusinessException;

    List<Album> searchAlbumsByName(String name) throws BusinessException;

    List<Album> searchAlbumsByGenre(Genre genre) throws BusinessException;
    List<Album> searchAlbumsByArtist(Artist artist) throws BusinessException;
}
