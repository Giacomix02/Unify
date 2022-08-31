package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.model.Album;
import it.univaq.disim.psvmsa.unify.model.Artist;
import it.univaq.disim.psvmsa.unify.model.Picture;

import java.io.InputStream;

public class FileArtistServiceImpl implements ArtistService {

    private static class Schema {

        public static int ARTIST_ID = 0;

        public static int ARTIST_NAME = 1;

        public static int ARTIST_BIOGRAPHY = 2;

    }

    private final String separator = "|";

    private IndexedFileLoader loader;

    private final String imageFolder;

    private InputStream stream;

    public FileArtistServiceImpl(String path, String imageFolderPath) {
        this.loader = new IndexedFileLoader(path, this.separator);
        this.imageFolder = imageFolderPath;
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
        artist.setPicture(new Picture(this.stream, artist.getId()));
        return artist;
    }

    @Override
    public void deleteById(Integer id) throws BusinessException {
        IndexedFile file = loader.load();
        file.deleteRowById(id);
        loader.save(file);
    }

    @Override
    public int add(Artist artist) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.separator);
        int id = file.incrementId();
        row.set(Schema.ARTIST_ID, artist.getId())
                .set(Schema.ARTIST_NAME, artist.getName())
                .set(Schema.ARTIST_BIOGRAPHY, artist.getBiography());
        file.appendRow(row);
        loader.save(file);
        return id;
    }

    @Override
    public void update(Artist artist) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.separator);
        row.set(Schema.ARTIST_ID, artist.getId())
                .set(Schema.ARTIST_NAME, artist.getName())
                .set(Schema.ARTIST_BIOGRAPHY, artist.getBiography());
        file.updateRow(row);
        loader.save(file);
    }

    @Override
    public void delete(Artist artist) throws BusinessException {
        deleteById(artist.getId());
    }
}
