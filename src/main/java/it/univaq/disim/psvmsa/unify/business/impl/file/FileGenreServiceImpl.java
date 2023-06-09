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

    private final IndexedFileLoader loader;

    public FileGenreServiceImpl(String path) {
        this.loader = new IndexedFileLoader(path, this.SEPARATOR, Schema.GENRE_ID);
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
        IndexedFile.Row row = loader.getRowById(id);
        if (row == null) return null;
        return new Genre(
                row.getIntAt(Schema.GENRE_ID),
                row.getStringAt(Schema.GENRE_NAME)
        );
    }

    @Override
    public List<Genre> searchByName(String name){
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.filterRows(
                r -> r.getStringAt(FileGenreServiceImpl.Schema.GENRE_NAME).toLowerCase().contains(name.toLowerCase())
        );
        List<Genre> genres = new ArrayList<>();
        for (IndexedFile.Row row : rows) {
            Genre genre = this.getById(row.getIntAt(FileGenreServiceImpl.Schema.GENRE_ID));
            genres.add(genre);
        }
        return genres;
    }

    @Override
    public Genre add(Genre genre)  {
        if(existGenreName(genre.getName())) return null;
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
    public Genre upsert(Genre genre) throws BusinessException {
        if(existGenreName(genre.getName())) return genre;
        return add(genre);
    }

    public boolean existGenreName(String name) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRow(r -> r.getStringAt(Schema.GENRE_NAME).equalsIgnoreCase(name));
        return row != null;
    }

    @Override
    public void delete(Genre genre) throws BusinessException {
       if(loader.deleteRowById(genre.getId()) == null) throw new BusinessException("Genre not found");
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
