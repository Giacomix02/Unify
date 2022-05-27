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
        StorageKindInterface storage = new Memory(this.USER_TABLE);

        public int add(User user) throws KeyAlreadyExistsException{
                SetQuery query = new SetQuery(this.USER_TABLE);
                query
                        .set(UserSchema.USER_PASSWORD, user.getPassword())
                        .set(UserSchema.USER_NAME, user.getUsername());
                return this.storage.addOne(UserSchema.USER_ID.getRow(), query);
        }

        public void update(User user) throws KeyNotExistsException {
                SetQuery query = new SetQuery(this.USER_TABLE);
                query
                        .set(UserSchema.USER_ID, user.getUsername())
                        .set(UserSchema.USER_PASSWORD, user.getPassword())
                        .set(UserSchema.USER_NAME, user.getUsername());
                Integer id = user.getId();
                this.storage.updateOne(UserSchema.USER_ID.getRow(),id.toString() , query);
        }

        public User getById(String id){
                return this.getByProp(UserSchema.USER_ID, id);
        }

        public User getByProp(UserSchema prop, String key) {
                GetQuery data = this.storage.getOne(prop.getRow(), key);
                return new User(
                        data.getColumnAsString(UserSchema.USER_NAME),
                        data.getColumnAsString(UserSchema.USER_PASSWORD),
                        data.getColumnAsInt(UserSchema.USER_ID)
                );
        }

        public void removeById(String id) {
                this.storage.removeOne(UserSchema.USER_ID.getRow(), id);
        }

}
