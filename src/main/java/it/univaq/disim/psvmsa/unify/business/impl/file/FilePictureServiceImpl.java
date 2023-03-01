package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.model.Picture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

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
        ensurePictureFolderExists();
    }
    @Override
    public Picture getById(Integer id) {
        IndexedFile.Row row = loader.getRowById(id);
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
        if(loader.deleteRowById(id) == null) throw new BusinessException("Picture not found");
        File fileToDelete = new File(this.imageFolder + id);
        if(fileToDelete.exists()) fileToDelete.delete();
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
    public Picture update(Picture picture) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRow(r -> r.getIntAt(Schema.PICTURE_ID) == picture.getId());
        if (row == null) throw new BusinessException("Picture not found");
        row.set(Schema.PICTURE_ID, picture.getId());
        this.savePictureToFile(picture);
        file.updateRow(row);
        loader.save(file);
        return picture;
    }

    @Override
    public void delete(Picture picture) throws BusinessException {
        this.deleteById(picture.getId());
    }

    private void savePictureToFile(Picture picture){
        File fileToSave = new File(this.imageFolder + picture.getId() + ".png");
        try {
            BufferedImage img = ImageIO.read(picture.toStream());
            ImageIO.write(img, "png", fileToSave);
        } catch (IOException e) {
            //TODO maybe relaunch error
            e.printStackTrace();
        }
    }

    private InputStream getPictureStream(Integer id){
        try{
            File file = new File(this.imageFolder + id + ".png");
            return new FileInputStream(file);
        }catch(Exception e){
            e.printStackTrace();
            //TODO maybe relaunch error
            return null;
        }
    }

    private void ensurePictureFolderExists(){
        File folder = new File(this.imageFolder);
        System.out.println(folder.exists());
        if(!folder.exists()) folder.mkdirs();
    }
}
