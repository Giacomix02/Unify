package it.univaq.disim.psvmsa.unify.storage;

import it.univaq.disim.psvmsa.unify.storage.kinds.StorageKindInterface;
import it.univaq.disim.psvmsa.unify.storage.schema.UserSchema;

public class UserStorage implements StorageInterface<Object>{

        StorageKindInterface storage;

        public void add(String key, Object value) {

        }

        public void update(String key, Object value) {

        }

        public Object get(String key) {
            return null;
        }

        public Object getProp(String key, UserSchema prop) {
                return null;
        }

        public void remove(String key) {

        }

        public void clear() {

        }

}
