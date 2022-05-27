package it.univaq.disim.psvmsa.unify.storage.queries;

import java.util.ArrayList;
import java.util.List;

public abstract class Query<T extends Object> {
    private List<T> data;
    private String table;

    public Query(String table, List<T> data){
        this.table = table;
        this.data = data;
    }
    public String[] asStringArray(){
        return (String[]) data.toArray();
    }
    public List<T> getData(){
        return data;
    }
    public void setData(List<T> data){
        this.data = data;
    }
    public String getTable(){
        return this.table;
    }
    public void setTable(String table){
        this.table = table;
    }
    public void ensureSize(int required){
        for (int i = data.size(); i < required + 1; i++)
            data.add(null);
    }
    abstract public String serialize();

}
