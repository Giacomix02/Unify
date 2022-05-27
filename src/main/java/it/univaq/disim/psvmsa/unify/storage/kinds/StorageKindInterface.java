package it.univaq.disim.psvmsa.unify.storage.kinds;

import it.univaq.disim.psvmsa.unify.storage.KeyAlreadyExistsException;
import it.univaq.disim.psvmsa.unify.storage.KeyNotExistsException;
import it.univaq.disim.psvmsa.unify.storage.queries.GetQuery;
import it.univaq.disim.psvmsa.unify.storage.queries.SetQuery;

import java.util.List;

public interface StorageKindInterface {

    int addOne(int idColumn, SetQuery value);

    void updateOne(int queryColumn, int key, SetQuery value) throws KeyNotExistsException;

    GetQuery getOne(int queryColumn, int key);

    List<GetQuery> getMany(int queryColumn, int key);

    void removeOne(int queryColumn,int key);

}
