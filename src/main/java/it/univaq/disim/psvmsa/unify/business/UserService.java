package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.User;

public interface UserService {

    User getById(Integer id);

    User getByUsername(String username);

    User validate(String username, String password) throws BusinessException;

    void delete(User user) throws BusinessException;

    void update(User user) throws BusinessException;

    User add(User user) throws BusinessException;

}
