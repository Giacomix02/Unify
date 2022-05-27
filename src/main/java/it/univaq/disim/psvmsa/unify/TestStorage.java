package it.univaq.disim.psvmsa.unify;

import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.storage.UserStorage;
import it.univaq.disim.psvmsa.unify.storage.schema.UserSchema;

public class TestStorage {

    public TestStorage(){

        User user1 = new User("user", "password");
        User user2 = new User("user2", "password2");
        UserStorage storage = new UserStorage();
        int id = storage.add(user1);
        System.out.println("User added with id: " + id);
        id = storage.add(user2);
        System.out.println("User added with id: " + id);
        User userTest = storage.getById(id-1);
        System.out.println("User retrieved: " + userTest.toString());
        User userTest2 = storage.getByProp(UserSchema.USER_ID, id);
        System.out.println("User retrieved: " + userTest2.toString());
    }
}
