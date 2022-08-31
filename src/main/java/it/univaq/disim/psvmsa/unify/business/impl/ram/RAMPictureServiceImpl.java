package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.model.Picture;

import java.util.HashMap;
import java.util.Map;

public class RAMPictureServiceImpl implements PictureService {

    private Map<Integer, Picture> pictures = new HashMap<>();
    private Integer id = 0;

    @Override
    public Picture getById(Integer id) {
        return this.pictures.get(id);
    }

    @Override
    public Picture add(Picture picture) {
        picture.setId(++id);
        this.pictures.put(picture.getId(), picture);
        return picture;
    }

    @Override
    public void update(Picture picture) throws BusinessException {
        Picture existing = getById(picture.getId());
        if(existing == null) throw new BusinessException("Picture not found");
        pictures.put(picture.getId(), picture);
    }

    @Override
    public void delete(Picture picture) throws BusinessException {
        deleteById(picture.getId());
    }

    @Override
    public void deleteById(Integer id) throws BusinessException{
        Picture existing = this.pictures.remove(id);
        if(existing == null) throw new BusinessException("Picture not found");
    }
}

