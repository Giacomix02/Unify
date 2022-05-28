package it.univaq.disim.psvmsa.unify.business;
import it.univaq.disim.psvmsa.unify.model.User;

public interface UserService {

    User getById(Integer id);

    User validate(String username, String password) throws UserServiceException;


    void delete(User user);

    void update(User user);

    void create(User user);

}
