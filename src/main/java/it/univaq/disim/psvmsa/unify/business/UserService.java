package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.User;

public interface UserService {

    User getById(Integer id);
    void create(User user);
    boolean validate(String username, String password);

}