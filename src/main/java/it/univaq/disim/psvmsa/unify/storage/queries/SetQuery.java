package it.univaq.disim.psvmsa.unify.storage.queries;



import java.util.ArrayList;
import java.util.List;

public class SetQuery extends Query<String> {
    public SetQuery(String table){
        super(table, new ArrayList<String>());
    }
    public SetQuery(String table, List<String> data){
        super(table, data);
    }

    public SetQuery add(String value){
        getData().add(value);
        return this;
    }
    public String serialize(){
        return super.getData().toString();
    }
}