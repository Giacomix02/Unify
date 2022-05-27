package it.univaq.disim.psvmsa.unify.storage.kinds;

import it.univaq.disim.psvmsa.unify.storage.KeyAlreadyExistsException;
import it.univaq.disim.psvmsa.unify.storage.KeyNotExistsException;
import it.univaq.disim.psvmsa.unify.storage.queries.GetQuery;
import it.univaq.disim.psvmsa.unify.storage.queries.SetQuery;

public interface StorageKindInterface {

    int addOne(int idColumn, SetQuery value) throws KeyAlreadyExistsException;

    void updateOne(int queryColumn, int key, SetQuery value) throws KeyNotExistsException;

    GetQuery getOne(int queryColumn, int key);

    GetQuery[] getMany(int queryColumn, int key);

    void removeOne(int queryColumn,int key);

}
