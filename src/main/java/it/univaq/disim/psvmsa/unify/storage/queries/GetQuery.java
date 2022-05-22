package it.univaq.disim.psvmsa.unify.storage.queries;

import java.util.List;

public class GetQuery extends Query {

    public GetQuery(String table,  List<Object> data){
        super(table, data);
    }

    public Object getColumn(int index){
        return getData().get(index);
    }

    public String toString(){
        return getData().toString();
    }
}
