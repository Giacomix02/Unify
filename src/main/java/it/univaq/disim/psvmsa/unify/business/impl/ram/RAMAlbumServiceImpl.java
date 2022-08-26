package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.model.Album;

import java.util.ArrayList;
import java.util.List;

public class RAMAlbumServiceImpl implements AlbumService {

    private List<Album> albums = new ArrayList<>();
    private Integer id = 0;


    @Override
    public Album getById(Integer id){
        for(Album album : albums){
            if(album.getId().equals(id)) return album;
        }
        return null;
    }

    @Override
    public void add(Album album){
        this.albums.add(album);
        album.setId(++id);
    }

    @Override
    public void update(Album album) throws  BusinessException {
        Album existing = getById(album.getId());
        if(existing==null) throw new BusinessException("Album not found");
        existing.setFrom(album);    //experimental function
    }

    @Override
    public void delete(Album album) throws BusinessException {
        if (this.albums.contains(album)) {
            this.albums.remove(album);
        } else {
            throw new BusinessException("Album not found");
        }
    }

    @Override
    public void deleteById(Integer id) {

        this.albums.remove(getById(id));
    }
}
