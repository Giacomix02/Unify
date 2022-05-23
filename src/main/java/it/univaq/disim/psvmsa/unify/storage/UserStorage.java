package it.univaq.disim.psvmsa.unify.storage;

import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.storage.kinds.StorageKindInterface;
import it.univaq.disim.psvmsa.unify.storage.schema.UserSchema;

public class UserStorage implements StorageInterface<User>{
        private final String USER_TABLE = "users";
        StorageKindInterface storage;

        public void add(String key, User value) {

        }

        public void update(String key, User value) {

        }

        public User get(String key) {
            return null;
        }

        public User getProp(String key, UserSchema prop) {
                return null;
        }

        public void remove(String key) {

        }

        public void clear() {

        }

}
