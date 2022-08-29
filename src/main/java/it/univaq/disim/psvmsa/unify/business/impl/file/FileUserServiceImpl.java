package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.UserService;
import it.univaq.disim.psvmsa.unify.model.User;

public class FileUserServiceImpl implements UserService {
    private static class Schema{
        public static int USER_ID = 0;
        public static int USER_NAME = 1;
        public static int PASSWORD = 2;
    }
    private final String separator = "|";
    IndexedFileLoader loader;
    public FileUserServiceImpl(String path){
        this.loader = new IndexedFileLoader(path, this.separator);
        try {
            this.add(new User("admin \n test new line", "admin"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public User getById(Integer id) {
        return null;
    }

    @Override
    public User getByUsername(String username) {
        return null;
    }

    @Override
    public User validate(String username, String password) throws BusinessException {
        return null;
    }

    @Override
    public void delete(User user) throws BusinessException {

    }

    @Override
    public void update(User user) throws BusinessException {

    }

    @Override
    public int add(User user) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.separator);
        int id = file.incrementId();
        row.set(Schema.USER_ID,id)
            .set(Schema.USER_NAME,user.getUsername())
            .set(Schema.PASSWORD,user.getPassword());
        file.appendRow(row);
        loader.save(file);
        return id;
    }
}
