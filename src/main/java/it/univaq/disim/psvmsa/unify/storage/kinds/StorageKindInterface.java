package it.univaq.disim.psvmsa.unify.storage.kinds;

import it.univaq.disim.psvmsa.unify.storage.exceptions.KeyAlreadyExistsException;
import it.univaq.disim.psvmsa.unify.storage.exceptions.KeyNotExistsException;
import it.univaq.disim.psvmsa.unify.storage.queries.GetQuery;
import it.univaq.disim.psvmsa.unify.storage.queries.SetQuery;

public interface StorageKindInterface {

    public void add(String key, SetQuery value) throws KeyAlreadyExistsException;

    public void update(String key, SetQuery value) throws KeyNotExistsException;

    public GetQuery get(String key);

    public void remove(String key);

    public void clear();

}
