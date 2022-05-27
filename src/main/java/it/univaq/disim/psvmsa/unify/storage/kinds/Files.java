package it.univaq.disim.psvmsa.unify.storage.kinds;

import it.univaq.disim.psvmsa.unify.storage.KeyAlreadyExistsException;
import it.univaq.disim.psvmsa.unify.storage.KeyNotExistsException;
import it.univaq.disim.psvmsa.unify.storage.queries.GetQuery;
import it.univaq.disim.psvmsa.unify.storage.queries.SetQuery;

import java.util.List;

public class Files implements StorageKindInterface {
    private String fileName;

    public Files(String fileName) {
        this.fileName = fileName;
    }
    public int addOne(int idColumn, SetQuery value) {
        return 1;
    }

    public void updateOne(int queryColumn, int key, SetQuery value) throws KeyNotExistsException {

    }

    public GetQuery getOne(int queryColumn, int key) {
        return null;
    }

    public List<GetQuery> getMany(int queryColumn, int key) {
        return null;
    }

    public void removeOne(int queryColumn, int key) {

    }
}
