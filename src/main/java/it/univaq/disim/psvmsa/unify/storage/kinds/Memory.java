package it.univaq.disim.psvmsa.unify.storage.kinds;

import it.univaq.disim.psvmsa.unify.storage.KeyAlreadyExistsException;
import it.univaq.disim.psvmsa.unify.storage.KeyNotExistsException;
import it.univaq.disim.psvmsa.unify.storage.queries.GetQuery;
import it.univaq.disim.psvmsa.unify.storage.queries.SetQuery;

import java.util.ArrayList;
import java.util.List;

public class Memory implements StorageKindInterface{

        private String tableName;
        private List<Integer> primaryKeys = new ArrayList<Integer>();
        private List<List<String>> data = new ArrayList<List<String>>();

        public Memory(String tableName) {
                this.tableName = tableName;
        }

        public int addOne(int idColumn, SetQuery value) throws KeyAlreadyExistsException {
                int id = getPrimaryKey(idColumn) + 1;
                value.set(idColumn, String.valueOf(id));
                setPrimaryKey(idColumn, id);
                data.add(value.getData());
                return id;
        }
        private Integer getPrimaryKey(int index){
                Integer key = primaryKeys.get(index);
                if(key == null) {
                        primaryKeys.set(index, 0);
                        key = 0;
                }
                return key;
        }
        private Integer setPrimaryKey(int index, Integer value){
                return primaryKeys.set(index, value);
        }
        private boolean ensureUnique(){
                return true;
        }
        public void updateOne(int queryColumn, int key, SetQuery value) throws KeyNotExistsException {
                int index = -1;
                int i = 0;
                String keyString = String.valueOf(key);
                for(List<String> row : data){
                        if(row.get(queryColumn).equals(keyString)){
                                index = i;
                                break;
                        }
                        i++;
                }
                if(index < 0)
                        throw new KeyNotExistsException(this.tableName + "." + queryColumn + "=" + key + " not found");
                data.set(index, value.getData());
        }
        public GetQuery getOne(int queryColumn, int key) {
                String id = String.valueOf(key);
                GetQuery query = new GetQuery(tableName);
                for(List<String> row : data){
                        if(row.get(queryColumn).equals(id)){
                                query.setData(row);
                                return query;
                        }
                }
                return null;
        }

        public GetQuery[] getMany(int queryColumn, int key) {
                return null;
        }

        public void removeOne(int queryColumn, int key) {

        }
}
