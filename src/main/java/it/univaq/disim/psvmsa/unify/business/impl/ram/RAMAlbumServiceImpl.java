package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.SongService;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Genre;
import it.univaq.disim.psvmsa.unify.model.Song;

import java.util.*;

public class RAMAlbumServiceImpl implements AlbumService {

    private Map<Integer,Album> albums = new HashMap<>();
    private Integer id = 0;
    private SongService songService;

    public RAMAlbumServiceImpl(SongService songService){
        this.songService = songService;
    }

    @Override
    public Album getById(Integer id){
        return this.albums.get(id);
    }


    @Override
    public Album add(Album album) throws BusinessException {
        album.setId(++id);
        this.albums.put(album.getId(),album);
        for(Song song : album.getSongs()){
            songService.upsert(song);
        }
        return album;
    }

    @Override
    public void update(Album album) throws  BusinessException {
        Album existing = getById(album.getId());
        //find and delete songs that were removed from this album
        if(existing==null) throw new BusinessException("Album not found");
        for(Song song : existing.getSongs()){
            if(album.getSongs().stream().noneMatch(s -> Objects.equals(s.getId(), song.getId()))){
                songService.delete(song);
            }else{
                songService.upsert(song);
            }
        }
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
        for(Song song : existing.getSongs()){
            songService.delete(song);
        }
    }
}
