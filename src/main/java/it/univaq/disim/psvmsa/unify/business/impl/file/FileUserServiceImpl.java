package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.User;

public class FileUserServiceImpl implements UserService {
    @Override
    public User getById(Integer id) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public User validate(String username, String password) throws BusinessException {
        return null;
    }

    @Override
    public void delete(User user) throws BusinessException {

    }

    @Override
    public void update(User user) throws BusinessException {

    }

    @Override
    public void add(User user) throws BusinessException {

    }
}
