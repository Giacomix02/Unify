package it.univaq.disim.psvmsa.unify.storage.queries;

import it.univaq.disim.psvmsa.unify.storage.schema.SchemaInterface;

import java.util.List;

public class GetQuery extends Query<String> {

    public GetQuery(String table, List<String> data) {
        super(table, data);
    }

    public String getColumn(int index) {
        return getData().get(index);
    }

    public String getColumnAsString(int index) {
        return getColumn(index);
    }
    public String getColumnAsString(SchemaInterface prop){
        return getColumnAsString(prop.getRow());
    }
    public int getColumnAsInt(int index) {
        return Integer.parseInt(getColumn(index));
    }
    public int getColumnAsInt(SchemaInterface prop){
        return getColumnAsInt(prop.getRow());
    }
    public String serialize() {
        return getData().toString();
    }
}
