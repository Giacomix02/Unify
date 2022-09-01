package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.GenreService;
import it.univaq.disim.psvmsa.unify.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class FileGenreServiceImpl implements GenreService {

    private static class Schema {

        public static int GENRE_ID = 0;
        public static int GENRE_NAME = 1;

    }

    private final String SEPARATOR = "|";

    private IndexedFileLoader loader;

    public FileGenreServiceImpl(String path) {
        this.loader = new IndexedFileLoader(path, this.SEPARATOR);
    }

    @Override
    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.getRows();
        for (IndexedFile.Row row : rows) {
            genres.add(new Genre(
                    row.getIntAt(Schema.GENRE_ID),
                    row.getStringAt(Schema.GENRE_NAME)
            ));
        }
        return genres;
    }

    @Override
    public Genre getById(Integer id) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRow(r -> r.getIntAt(Schema.GENRE_ID) == (id));
        if (row == null) return null;
        return new Genre(
                row.getIntAt(Schema.GENRE_ID),
                row.getStringAt(Schema.GENRE_NAME)
        );
    }

    @Override
    public Genre add(Genre genre) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        int id = file.incrementId();
        genre.setId(id);
        row.set(Schema.GENRE_ID, genre.getId())
                .set(Schema.GENRE_NAME, genre.getName());
        file.appendRow(row);
        loader.save(file);
        return genre;
    }

    @Override
    public void delete(Genre genre) throws BusinessException {
        IndexedFile file = loader.load();
        file.deleteRowById(genre.getId());
        loader.save(file);
    }

    @Override
    public void update(Genre genre) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        row.set(Schema.GENRE_ID, genre.getId())
                        .set(Schema.GENRE_NAME, genre.getName());
        file.updateRow(row);
        loader.save(file);
    }
}
