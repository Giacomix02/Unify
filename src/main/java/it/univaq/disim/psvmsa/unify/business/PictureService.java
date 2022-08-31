package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Picture;

import javafx.scene.image.Image;

public interface PictureService {

    Picture getById(Integer id);

    void deleteById (Integer id) throws BusinessException;

    int add(Picture picture);

    void update (Picture picture) throws BusinessException;

    void delete (Picture picture) throws BusinessException;


}
