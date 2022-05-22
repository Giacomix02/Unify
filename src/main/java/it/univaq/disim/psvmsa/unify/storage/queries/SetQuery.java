package it.univaq.disim.psvmsa.unify.storage.queries;


import it.univaq.disim.psvmsa.unify.storage.queries.Query;

import java.util.List;

public class SetQuery extends Query {
    public SetQuery(String table, List<Object> data){
        super(table, data);
    }

    public String toString(){
        return super.getData().toString();
    }
}