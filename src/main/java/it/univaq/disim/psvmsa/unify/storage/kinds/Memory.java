package it.univaq.disim.psvmsa.unify.storage.kinds;

import it.univaq.disim.psvmsa.unify.storage.KeyAlreadyExistsException;
import it.univaq.disim.psvmsa.unify.storage.KeyNotExistsException;
import it.univaq.disim.psvmsa.unify.storage.queries.GetQuery;
import it.univaq.disim.psvmsa.unify.storage.queries.SetQuery;

public class Memory implements StorageKindInterface{

        private String tableName;
        public Memory(String tableName) {
                this.tableName = tableName;
        }

        public void addOne(int queryColumn, String key, SetQuery value) throws KeyAlreadyExistsException {

        }

        public void updateOne(int queryColumn, String key, SetQuery value) throws KeyNotExistsException {

        }

        public GetQuery getOne(int queryColumn, String key) {
                return null;
        }

        public GetQuery[] getMany(int queryColumn, String key) {
                return null;
        }

        public void removeOne(int queryColumn, String key) {

        }
}
