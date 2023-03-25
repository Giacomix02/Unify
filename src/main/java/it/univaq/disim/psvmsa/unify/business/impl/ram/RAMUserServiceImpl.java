package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RAMUserServiceImpl implements UserService   {

    private Map<Integer, User> users = new HashMap<Integer, User>();

    private Integer id = 0;

    @Override
    public User getById(Integer id) {
        return this.users.get(id);
    }
    @Override
    public User validate(String username, String password) throws BusinessException {
        User user = this.getByUsername(username);
        if(user != null && user.getPassword().equals(password)) return user;
        throw new BusinessException("Username and/or password incorrect");
    }
    @Override
    public void delete(User user) throws BusinessException {
        User existingUser = this.getById(user.getId());
        if(existingUser == null) throw new BusinessException("User not found");
        users.remove(existingUser.getId());
    }
    @Override
    public void update(User user) throws BusinessException {
        User existingUser = this.getById(user.getId());
        if(existingUser == null) throw new BusinessException("User not found");
        users.put(user.getId(), user);
    }
    @Override
    public User add(User user) throws BusinessException {
        if (getByUsername(user.getUsername()) != null) throw new BusinessException("User already exists");
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User getByUsername(String username) {
        for (User user : users.values()) {
            if (user.getUsername().equals(username)) return user;
        }
        return null;
    }

}
