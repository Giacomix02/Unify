package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PlaylistService;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.ArrayList;
import java.util.List;

public class FileUserServiceImpl implements UserService {
    private static class Schema{
        public static int USER_ID = 0;
        public static int USER_NAME = 1;
        public static int PASSWORD = 2;
    }
    private final String separator = "|";
    private final IndexedFileLoader loader;

    public FileUserServiceImpl(String path){
        this.loader = new IndexedFileLoader(path, this.separator, Schema.USER_ID);
    }
    @Override
    public User getById(Integer id) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRowById(id);
        if (row == null) return null;
        return new User(
                row.getStringAt(Schema.USER_NAME),
                row.getStringAt(Schema.PASSWORD),
                row.getIntAt(Schema.USER_ID)
        );
    }

    public User getByUsername(String username) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRow(r -> r.getStringAt(Schema.USER_NAME).equals(username));
        if(row == null) return null;
        return new User(
                row.getStringAt(Schema.USER_NAME),
                row.getStringAt(Schema.PASSWORD),
                row.getIntAt(Schema.USER_ID)
        );
    }

    @Override
    public User validate(String username, String password) throws BusinessException {
        User user = getByUsername(username);
        if(user == null) throw new BusinessException("Username / password is incorrect");
        if(user.getPassword().equals(password)) {
            return user;
        }
        throw new BusinessException("Username / password is incorrect");
    }

    @Override
    public void delete(User user) throws BusinessException {
        User existingUser = this.getById(user.getId());
        if(existingUser == null) throw new BusinessException("User not found");
        IndexedFile file = loader.load();
        file.deleteRowById(user.getId());
        loader.save(file);
    }

    @Override
    public void update(User user) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.separator);
        row.set(Schema.USER_ID, user.getId())
                .set(Schema.USER_NAME, user.getUsername())
                .set(Schema.PASSWORD, user.getPassword());
        file.updateRow(row);
        loader.save(file);
    }

    @Override
    public User add(User user) throws BusinessException {
        User existingUser = getByUsername(user.getUsername());
        if(existingUser != null) throw new BusinessException("User already exists");
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.separator);
        int id = file.incrementId();
        user.setId(id);
        row.set(Schema.USER_ID,user.getId())
            .set(Schema.USER_NAME,user.getUsername())
            .set(Schema.PASSWORD,user.getPassword());
        file.appendRow(row);
        loader.save(file);
        return user;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.getRows();
        for (IndexedFile.Row row : rows) {
            users.add(new User(
                    row.getStringAt(Schema.USER_NAME),
                    row.getStringAt(Schema.PASSWORD),
                    row.getIntAt(Schema.USER_ID)
            ));
        }
        return users;
    }
}
