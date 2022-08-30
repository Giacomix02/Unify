package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.model.Picture;

public class FilePictureServiceImpl implements PictureService {

    private static class Schema {

        private static int PICTURE_ID = 0;

        private static int PICTURE_IMAGE = 1;

    }

    private final String separator = "|";

    IndexedFileLoader loader;

    @Override
    public Picture getById(Integer id) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRow(r -> r.getIntAt(Schema.PICTURE_ID) == id);
        if (row == null) return null;
        return new Picture(
                row.get(Schema.PICTURE_IMAGE),
                row.getIntAt(Schema.PICTURE_ID));
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public int add(Picture picture) {
        return 0;
    }

    @Override
    public void update(Picture picture) throws BusinessException {

    }

    @Override
    public void delete(Picture picture) throws BusinessException {

    }
}
