package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.model.Album;

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
    public void delete(Album album) throws BusinessException{
        deleteById(album.getId());
    }

    @Override
    public List<Album> searchAlbumsByName(String name) {
        return null;
    }

    @Override
    public void deleteById(Integer id) throws BusinessException{
        Album existing = this.albums.remove(id);
        if(existing==null) throw new BusinessException("Album not found");
    }
}
