package it.univaq.disim.psvmsa.unify.storage;

import it.univaq.disim.psvmsa.unify.model.User;
import it.univaq.disim.psvmsa.unify.storage.kinds.Memory;
import it.univaq.disim.psvmsa.unify.storage.kinds.StorageKindInterface;
import it.univaq.disim.psvmsa.unify.storage.queries.GetQuery;
import it.univaq.disim.psvmsa.unify.storage.queries.SetQuery;
import it.univaq.disim.psvmsa.unify.storage.schema.UserSchema;

// THIS IS ONLY A MOCK PROTOTYPE TO SEE IF IT'S DOABLE TO USE A SORT OF "ORM"
public class UserStorage {
        private final String USER_TABLE = "users";
        private static StorageKindInterface storage = new Memory("users");

        public int add(User user){
                SetQuery query = new SetQuery(this.USER_TABLE);
                query
                        .set(UserSchema.USER_PASSWORD, user.getPassword())
                        .set(UserSchema.USER_NAME, user.getUsername());
                return storage.addOne(UserSchema.USER_ID.getRow(), query);
        }

        public void update(User user) throws KeyNotExistsException {
                SetQuery query = new SetQuery(this.USER_TABLE);
                query
                        .set(UserSchema.USER_ID, user.getUsername())
                        .set(UserSchema.USER_PASSWORD, user.getPassword())
                        .set(UserSchema.USER_NAME, user.getUsername());
                storage.updateOne(UserSchema.USER_ID.getRow(),user.getId() , query);
        }

        public User getById(Integer id){
                return this.getByProp(UserSchema.USER_ID, id);
        }

        public User getByProp(UserSchema prop, Integer key) {
                GetQuery data = storage.getOne(prop.getRow(), key);
                if(data == null) return null;
                return new User(
                        data.getColumnAsString(UserSchema.USER_NAME),
                        data.getColumnAsString(UserSchema.USER_PASSWORD),
                        data.getColumnAsInt(UserSchema.USER_ID)
                );
        }

        public void removeById(Integer id) {
                storage.removeOne(UserSchema.USER_ID.getRow(), id);
        }

}
