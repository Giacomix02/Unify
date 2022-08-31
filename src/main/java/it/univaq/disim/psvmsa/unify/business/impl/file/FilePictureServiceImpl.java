package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.model.Picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

public class FilePictureServiceImpl implements PictureService {

    private static class Schema {
        private static int PICTURE_ID = 0;

    }

    private final String SEPARATOR = "|";
    private final IndexedFileLoader loader;
    private final String imageFolder;

    public FilePictureServiceImpl(String path, String imageFolderPath) {
        loader = new IndexedFileLoader(path, this.SEPARATOR, Schema.PICTURE_ID);
        this.imageFolder = imageFolderPath;
    }
    @Override
    public Picture getById(Integer id) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRow(r -> r.getIntAt(Schema.PICTURE_ID) == id);
        if (row == null) return null;
        try{
            InputStream stream = this.getPictureStream(row.getIntAt(Schema.PICTURE_ID));
            if(stream == null) return null;
            return new Picture(
                    stream,
                    row.getIntAt(Schema.PICTURE_ID)
            );
        } catch (Exception e) {
            e.printStackTrace();
            //TODO maybe relaunch error
            return null;
        }
    }

    @Override
    public void deleteById(Integer id) throws BusinessException {
        IndexedFile file = loader.load();
        file.deleteRowById(id);
        File fileToDelete = new File(this.imageFolder + id);
        if(fileToDelete.exists()) fileToDelete.delete();
        loader.save(file);
    }

    @Override
    public Picture add(Picture picture) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        int id = file.incrementId();
        picture.setId(id);
        row.set(Schema.PICTURE_ID, picture.getId());
        this.savePictureToFile(picture);
        file.appendRow(row);
        loader.save(file);
        return picture;
    }
    @Override
    public void update(Picture picture) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRow(r -> r.getIntAt(Schema.PICTURE_ID) == picture.getId());
        if (row == null) throw new BusinessException("Picture not found");
        row.set(Schema.PICTURE_ID, picture.getId());
        this.savePictureToFile(picture);
        file.updateRow(row);
        loader.save(file);
    }

    @Override
    public void delete(Picture picture) throws BusinessException {
        IndexedFile file = loader.load();
        file.deleteRowById(picture.getId());
        File fileToDelete = new File(this.imageFolder + picture.getId());
        if(fileToDelete.exists()) fileToDelete.delete();
        loader.save(file);
    }

    private void savePictureToFile(Picture picture){
        File fileToSave = new File(this.imageFolder + picture.getId());
        try {
            BufferedImage img = ImageIO.read(picture.getImageStream());
            ImageIO.write(img, "png", fileToSave);
        } catch (IOException e) {
            //TODO maybe relaunch error
            e.printStackTrace();
        }
    }

    private InputStream getPictureStream(Integer id){
        try{
            //TODO might be able to use FileInputStream instead
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            BufferedImage img = ImageIO.read(
                    new URL(this.imageFolder + id)
            );
            ImageIO.write(img, "png", os);
            return new ByteArrayInputStream(os.toByteArray());
        }catch(Exception e){
            e.printStackTrace();
            //TODO maybe relaunch error
            return null;
        }
    }
}
