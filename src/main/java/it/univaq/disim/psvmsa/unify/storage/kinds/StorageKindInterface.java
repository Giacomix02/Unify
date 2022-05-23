package it.univaq.disim.psvmsa.unify.storage.kinds;

import it.univaq.disim.psvmsa.unify.storage.KeyAlreadyExistsException;
import it.univaq.disim.psvmsa.unify.storage.KeyNotExistsException;
import it.univaq.disim.psvmsa.unify.storage.queries.GetQuery;
import it.univaq.disim.psvmsa.unify.storage.queries.SetQuery;

public interface StorageKindInterface {

    void addOne(int queryColumn, String key, SetQuery value) throws KeyAlreadyExistsException;

    void updateOne(int queryColumn, String key, SetQuery value) throws KeyNotExistsException;

    GetQuery getOne(int queryColumn, String key);

    GetQuery[] getMany(int queryColumn, String key);

    void removeOne(int queryColumn,String key);

}
