package it.univaq.disim.psvmsa.unify.business.impl.file;
import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.model.Album;

import java.util.ArrayList;
import java.util.List;

public class FileAlbumServiceImpl implements AlbumService {

    private static class Schema {
        public static int ALBUM_ID = 0;
        public static int ALBUM_NAME = 1;
    }

    private final String SEPARATOR = "|";

    private final IndexedFileLoader loader;

    public FileAlbumServiceImpl(String path) {
        this.loader = new IndexedFileLoader(path, this.SEPARATOR);
    }

    @Override
    public Album getById(Integer id) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRowById(id);
        if (row == null) return null;
        return new Album(
                row.getIntAt(Schema.ALBUM_ID),
                row.getStringAt(Schema.ALBUM_NAME)
        );
    }

    @Override
    public List<Album> getAlbums(){
        List<Album> albums = new ArrayList<>();
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.getRows();
        for (IndexedFile.Row row : rows) {
            albums.add(new Album(
                    row.getIntAt(Schema.ALBUM_ID),
                    row.getStringAt(Schema.ALBUM_NAME)
            ));
        }
        return albums;
    }

    @Override
    public void deleteById(Integer id) throws BusinessException {
        IndexedFile file = loader.load();
        file.deleteRowById(id);
        loader.save(file);
    }

    @Override
    public Album add(Album album) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        int id = file.incrementId();
        album.setId(id);
        row.set(Schema.ALBUM_ID, album.getId())
                .set(Schema.ALBUM_NAME, album.getName());
        file.appendRow(row);
        loader.save(file);
        return album;
    }

    @Override
    public void update(Album album) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        row.set(Schema.ALBUM_ID, album.getId())
                .set(Schema.ALBUM_NAME, album.getName());
        file.updateRow(row);
        loader.save(file);
    }

    @Override
    public void delete(Album album) throws BusinessException {
        deleteById(album.getId());
    }

    @Override
    public List<Album> searchAlbumsByName(String name) {
        return null;
    }
}
