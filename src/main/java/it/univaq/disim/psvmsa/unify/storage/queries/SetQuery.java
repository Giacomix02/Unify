package it.univaq.disim.psvmsa.unify.storage.queries;



import it.univaq.disim.psvmsa.unify.storage.schema.SchemaInterface;

import java.util.ArrayList;
import java.util.List;

public class SetQuery extends Query<String> {
    public SetQuery(String table){
        super(table, new ArrayList<String>());
    }
    public SetQuery(String table, List<String> data){
        super(table, data);
    }
    public SetQuery set(SchemaInterface prop, String value){
        getData().set(prop.getRow(), value);
        return this;
    }
    public SetQuery set(int prop, String value){
        getData().set(prop, value);
        return this;
    }
    public String serialize(){
        return super.getData().toString();
    }
}