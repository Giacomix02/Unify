package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Genre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RAMAlbumServiceImpl implements AlbumService {

    private Map<Integer,Album> albums = new HashMap<>();
    private Integer id = 0;


    @Override
    public Album getById(Integer id){
        return this.albums.get(id);
    }


    @Override
    public Album add(Album album){
        album.setId(++id);
        this.albums.put(album.getId(),album);
        return album;
    }

    @Override
    public void update(Album album) throws  BusinessException {
        Album existing = getById(album.getId());
        if(existing==null) throw new BusinessException("Album not found");
        albums.put(album.getId(),album);

    }

    @Override
    public List<Album> getAlbums(){
        return new ArrayList<>(albums.values());
    }

    @Override
    public void delete(Album album) throws BusinessException{
        deleteById(album.getId());
    }

    @Override
    public Album upsert(Album album) throws BusinessException {
        if(album.getId()==null || getById(album.getId()) == null){
            return add(album);
        }else{
            update(album);
            return album;
        }
    }

    @Override
    public List<Album> searchAlbumsByName(String name) {
        return null;
    }

    @Override
    public List<Album> searchAlbumsByGenre(Genre genre) throws BusinessException {
        List<Album> albums = new ArrayList<>();
        for (Album album : this.albums.values()) {
            if(album.getGenre().equals(genre)){
                albums.add(album);
            }
        }
        return albums;
    }

    @Override
    public List<Album> searchAlbumsByArtist(Artist artist) throws BusinessException {
        List<Album> albums = new ArrayList<>();
        for (Album album : this.albums.values()) {
            if(album.getArtist().equals(artist)){
                albums.add(album);
            }
        }
        return albums;
    }

    @Override
    public void deleteById(Integer id) throws BusinessException{
        Album existing = this.albums.remove(id);
        if(existing==null) throw new BusinessException("Album not found");
    }
}
