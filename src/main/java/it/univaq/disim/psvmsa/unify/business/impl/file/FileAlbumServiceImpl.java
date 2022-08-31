package it.univaq.disim.psvmsa.unify.business.impl.file;
import it.univaq.disim.psvmsa.unify.business.AlbumService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.model.Album;

public class FileAlbumServiceImpl implements AlbumService {

    private static class Schema {

        public static int ALBUM_ID = 0;

        public static int ALBUM_NAME = 1;

    }

    private final String separator = "|";

    private IndexedFileLoader loader;

    public FileAlbumServiceImpl(String path) {
        this.loader = new IndexedFileLoader(path, this.separator);
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
    public void deleteById(Integer id) throws BusinessException {
        IndexedFile file = loader.load();
        file.deleteRowById(id);
        loader.save(file);
    }

    @Override
    public int add(Album album) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.separator);
        int id = file.incrementId();
        row.set(Schema.ALBUM_ID, album.getId())
                .set(Schema.ALBUM_NAME, album.getName());
        file.appendRow(row);
        loader.save(file);
        return id;
    }

    @Override
    public void update(Album album) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.separator);
        row.set(Schema.ALBUM_ID, album.getId())
                .set(Schema.ALBUM_NAME, album.getName());
        file.updateRow(row);
        loader.save(file);
    }

    @Override
    public void delete(Album album) throws BusinessException {
        deleteById(album.getId());
    }
}