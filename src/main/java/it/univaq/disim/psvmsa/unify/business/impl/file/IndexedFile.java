package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


//TODO dont use business exception here, create a new one
//TODO consider removing Row from static file here
public class IndexedFile {
    public static class Row {
        private List<String> values = new ArrayList<>();
        private String separator;

        public Row(String separator) {
            this.separator = separator;
        }

        public Row(String separator, List<String> values) {
            this.values = values;
            this.separator = separator;
        }

        static Row fromText(String separator, String line) {
            Row row = new Row(separator);
            String escaped = line.replaceAll("\\\\n", "\n");
            row.values = List.of(escaped.split(Pattern.quote(separator)));
            return row;
        }

        public Row set(int index, String value) {
            //TODO not sure if this is the best way to do this
            if(index >= values.size()) {
                for(int i = values.size(); i < index; i++) {
                    values.add(null);
                }
            }
            this.values.add(index, value);
            return this;
        }

        public Row set(int index, Integer value) {
            return this.set(index, value.toString());
        }

        public Row add(String value) {
            this.values.add(value);
            return this;
        }

        public Row add(Integer value) {
            return this.add(value.toString());
        }

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }

        public String getStringAt(int index) {
            return values.get(index);
        }

        public int getIntAt(int index) {
            return Integer.parseInt(values.get(index));
        }

        public String toTextRow() {
            return String.join(this.separator, values).replaceAll("\n", "\\\\n");
        }
    }

    private final int ID_POSITION;
    private int id = 0;
    private List<Row> rows = new ArrayList<>();

    public IndexedFile() {
        this.ID_POSITION = 0;
    }

    public IndexedFile(int ID_POSITION) {
        this.ID_POSITION = ID_POSITION;
    }

    public void appendRow(Row row) {
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

    public int incrementId() {
        return ++id;
    }

    public int getId() {
        return id;
    }

    public List<Row> filterRows(Predicate<Row> predicate) {
        return rows.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public Row findRow(Predicate<Row> predicate) {
        return rows.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public Row findRowById(int id) {
        return findRow(row -> row.getIntAt(ID_POSITION) == id);
    }

    public void deleteRowById(int id) throws BusinessException {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getIntAt(ID_POSITION) == id) {
                rows.remove(i);
                return;
            }
        }
        throw new BusinessException("Row doesn't exist");
    }

    public void updateRow(Row row) throws BusinessException {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getIntAt(ID_POSITION) == row.getIntAt(id)) {
                rows.set(i, row);
                return;
            }
        }
        throw new BusinessException("Row doesn't exist");
    }
}

