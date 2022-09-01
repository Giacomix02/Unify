package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Picture;

import java.util.List;

public class FileArtistServiceImpl implements ArtistService {

    private static class Schema {
        public static int ARTIST_ID = 0;
        public static int ARTIST_NAME = 1;
        public static int ARTIST_BIOGRAPHY = 2;
        public static int PICTURE_ID = 3;
    }

    private final String SEPARATOR = "|";
    private final IndexedFileLoader loader;
    private final PictureService pictureService;


    public FileArtistServiceImpl(String path, PictureService pictureService) {
        this.loader = new IndexedFileLoader(path, this.SEPARATOR, Schema.ARTIST_ID);
        this.pictureService = pictureService;
    }

    @Override
    public Artist getById(Integer id) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRowById(id);
        if (row == null) return null;
        Artist artist = new Artist(
                row.getIntAt(Schema.ARTIST_ID),
                row.getStringAt(Schema.ARTIST_NAME),
                row.getStringAt(Schema.ARTIST_BIOGRAPHY)
        );
        Picture picture = pictureService.getById(row.getIntAt(Schema.PICTURE_ID));
        artist.setPicture(picture);
        return artist;
    }

    @Override
    public void deleteById(Integer id) throws BusinessException {
        IndexedFile file = loader.load();
        file.deleteRowById(id);
        loader.save(file);
    }

    @Override
    public Artist add(Artist artist) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        int id = file.incrementId();
        artist.setId(id);
        row.set(Schema.ARTIST_ID, artist.getId())
                .set(Schema.ARTIST_NAME, artist.getName())
                .set(Schema.ARTIST_BIOGRAPHY, artist.getBiography())
                .set(Schema.PICTURE_ID, artist.getPicture().getId());
        file.appendRow(row);
        loader.save(file);
        return artist;
    }

    @Override
    public void update(Artist artist) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        row.set(Schema.ARTIST_ID, artist.getId())
                .set(Schema.ARTIST_NAME, artist.getName())
                .set(Schema.ARTIST_BIOGRAPHY, artist.getBiography())
                .set(Schema.PICTURE_ID, artist.getPicture().getId());
        file.updateRow(row);
        loader.save(file);
    }

    @Override
    public void delete(Artist artist) throws BusinessException {
        deleteById(artist.getId());
    }

    @Override
    public List<Artist> searchArtistsByName(String name) {
        return null;
    }
}
