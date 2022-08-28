package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.model.Picture;

import java.util.ArrayList;
import java.util.List;

public class RAMPictureServiceImpl implements PictureService {

    private List<Picture> pictures = new ArrayList<>();
    private Integer id = 0;

    @Override
    public Picture getById(Integer id) {
        for (Picture picture : pictures) {
            if (picture.getId().equals(id)) {
                return picture;
            }
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        for (Picture picture : pictures) {
            if (picture.getId().equals(id)) {
                pictures.remove(picture);
            }
        }
    }

    @Override
    public int add(Picture picture) {
        picture.setId(++id);
        pictures.add(picture);
        return picture.getId();
    }

    @Override
    public void delete(Picture picture) throws BusinessException {
        if (pictures.contains(picture)) {
            pictures.remove(picture);
        } else {
            throw new BusinessException("Picture not found");
        }
    }

    @Override
    public void update(Picture picture) throws BusinessException {
        int index = findIndexById(picture.getId());
        if (index < 0) throw new BusinessException("Picture not found");
        pictures.set(index, picture);
    }

    private int findIndexById(Integer id) {
        for (int i = 0; i < pictures.size(); i++) {
            if (pictures.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

}

