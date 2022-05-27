package it.univaq.disim.psvmsa.unify.storage.kinds;

import it.univaq.disim.psvmsa.unify.storage.ElementNotFoundException;
import it.univaq.disim.psvmsa.unify.storage.KeyAlreadyExistsException;
import it.univaq.disim.psvmsa.unify.storage.KeyNotExistsException;
import it.univaq.disim.psvmsa.unify.storage.queries.GetQuery;
import it.univaq.disim.psvmsa.unify.storage.queries.SetQuery;

import java.util.ArrayList;
import java.util.List;

public class Memory implements StorageKindInterface {

    //Could use hashset instead, but that would assume that there is a single unique primary key for each
    //entity, this is more flexible.
    private String tableName;
    private List<Integer> primaryKeys = new ArrayList<Integer>();
    private List<List<String>> data = new ArrayList<List<String>>();

    public Memory(String tableName) {
        this.tableName = tableName;
    }

    public int addOne(int idColumn, SetQuery value) {
        int id = getPrimaryKey(idColumn) + 1;
        setPrimaryKey(idColumn, id);
        value.set(idColumn, String.valueOf(id));
        data.add(value.getData());
        return id;
    }

    public void updateOne(int queryColumn, int key, SetQuery value) throws KeyNotExistsException {
        int index = getIndexOfFirstMatch(queryColumn, key);
        if (index < 0)
            throw new KeyNotExistsException(this.tableName + "." + queryColumn + "=" + key + " not found");
        data.set(index, value.getData());
    }

    public GetQuery getOne(int queryColumn, int key) {
        int index = getIndexOfFirstMatch(queryColumn, key);
        if (index < 0)
            return null;
        return new GetQuery(this.tableName, data.get(index));
    }

    public List<GetQuery> getMany(int queryColumn, int key) {
        List<GetQuery> result = new ArrayList<>();
        for(List<String> row : data){
            if(row.get(queryColumn).equals(String.valueOf(key))){
                result.add(new GetQuery(this.tableName, row));
            }
        }
        return result;
    }

    public void removeOne(int queryColumn, int key) throws ElementNotFoundException {
        int index = getIndexOfFirstMatch(queryColumn, key);
        if (index < 0)
            throw new ElementNotFoundException(this.tableName + "." + queryColumn + "=" + key + " not found");
        data.remove(index);
    }

    private int getIndexOfFirstMatch(int queryColumn, int id) {
        int index = -1;
        int i = 0;
        String keyString = String.valueOf(id);
        for (List<String> row : data) {
            if (row.get(queryColumn).equals(keyString)) {
                index = i;
                break;
            }
            i++;
        }
        return index;
    }

    private Integer getPrimaryKey(int index) {
        ensurePrimaryKeySize(index);
        return primaryKeys.get(index);
    }

    private void ensurePrimaryKeySize(int index) {
        for(int i = primaryKeys.size(); i <= index; i++){
            primaryKeys.add(i, 0);
        }
    }
    private Integer setPrimaryKey(int index, Integer value) {
        return primaryKeys.set(index, value);
    }
}
