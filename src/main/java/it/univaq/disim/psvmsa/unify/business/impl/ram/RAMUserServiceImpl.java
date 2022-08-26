package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.business.UserServiceException;
import it.univaq.disim.psvmsa.unify.model.Admin;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.ArrayList;
import java.util.List;

public class RAMUserServiceImpl implements UserService {

    private List<User> users = new ArrayList<>();

    private Integer id = 0;

    public void addMock() {
        User adminUser = new Admin("admin","admin");
        User user = new User("user","user");
        try{
            this.create(user);
            this.create(adminUser);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public User getById(Integer id) {
        for (User user : users) {
            if (user.getId().equals(id)) return user;
        }
        return null;
    }

    @Override
    public User validate(String username, String password) throws BusinessException {
        for (User user : users) {
            if (user.getPassword().equals(password) && user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new BusinessException("Username and/or password incorrect");
    }

    @Override
    public void delete(User user) throws BusinessException {
        int index = findIndexById(user.getId());
        if (index < 0) throw new BusinessException("User not found");
        users.remove(index);
    }

    @Override
    public void update(User user) throws BusinessException {
        int index = findIndexById(user.getId());
        if (index < 0) throw new BusinessException("User not found");
        users.set(index, user);
    }

    @Override
    public void create(User user) throws BusinessException {
        if (getByUsername(user.getUsername()) != null) throw new BusinessException("User already exists");
        user.setId(++id);
        users.add(user);

    }

    @Override
    public User getByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

    private int findIndexById(Integer id) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) return i;
        }
        return -1;
    }


}
