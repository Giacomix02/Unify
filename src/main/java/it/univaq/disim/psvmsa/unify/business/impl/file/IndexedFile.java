package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IndexedFile {
    public static class Row{
        private List<String> values = new ArrayList<String>();
        private String separator;
        public Row(String separator){
            this.separator = separator;
        }
        public Row(String separator, List<String> values) {
            this.values = values;
            this.separator = separator;
        }
        static Row fromText(String line, String separator){
            Row row = new Row(separator);
            row.values = List.of(line.split(separator));
            return row;
        }
        public List<String> getValues() {
            return values;
        }
        public void setValues(List<String> values) {
            this.values = values;
        }
        public String getStringAt(int index){
           return values.get(index);
        }
        public int getIntAt(int index){
            return Integer.parseInt(values.get(index));
        }
        public String toTextRow(){
            return String.join(this.separator, values).replaceAll("\n","\\\\n");
        }
    }

    private int id;
    private List<Row> rows = new ArrayList<>();

    public void appendRow(Row row){
        rows.add(row);
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int incrementId(){
        return ++id;
    }
    public int getId() {
        return id;
    }
    public List<Row> filterRows(Predicate<Row> predicate){
        return rows.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public Row findRow(Predicate<Row> predicate){
        return rows.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public Row findRowById(int id){
        return rows.stream()
                .filter(r -> r.getIntAt(0) == id)
                .findFirst()
                .orElse(null);
    }

    public void deleteRow(int id) throws BusinessException {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getIntAt(0) == id){
                rows.remove(i);
                return;
            }
        }
        throw new BusinessException("Row not found");
    }
}
