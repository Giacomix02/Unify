package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Picture;

public interface PictureService {

    Picture getById(Integer id);

    void deleteById (Integer id);

    void create (Picture picture);

    void update (Picture picture) throws BusinessException;

    void delete (Picture picture) throws BusinessException;


}
