package it.univaq.disim.psvmsa.unify.storage.queries;

import java.util.ArrayList;
import java.util.List;

public abstract class Query {
    private List<Object> data = new ArrayList<Object>();
    private String table;

    public Query(String table, List<Object> data){
        this.table = table;
        this.data = data;
    }

    public List<Object> getData(){
        return data;
    }
    public void setData(List<Object> data){
        this.data = data;
    }
    public String getTable(){
        return this.table;
    }
    public void setTable(String table){
        this.table = table;
    }
    abstract public String toString();
}
