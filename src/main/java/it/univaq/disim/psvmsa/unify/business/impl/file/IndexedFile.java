package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
            for (int i = this.values.size() - 1; index > i; i++) {
                    this.values.add(null);
            }
            this.values.set(index, value);
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
        public String toString(){
            return this.toTextRow();
        }
    }

    private final int ID_POSITION;
    private int currentId = 0;
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

    public void setCurrentId(int currentId) {
        this.currentId = currentId;
    }

    public int incrementId() {
        return ++currentId;
    }

    public int getCurrentId() {
        return currentId;
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

    public Row deleteRowById(int id) throws BusinessException{
        ListIterator<Row> listIterator = this.rows.listIterator();
        while (listIterator.hasNext()) {
            Row current = listIterator.next();
            if (listIterator.next().getIntAt(ID_POSITION) == id) {
                listIterator.remove();
                return current;
            }
        }
        throw new BusinessException("Row doesn't exist");
    }

    public void updateRow(Row row) throws BusinessException{
        ListIterator<Row> listIterator = this.rows.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next().getIntAt(ID_POSITION) == row.getIntAt(ID_POSITION)) {
                listIterator.set(row);
                return;
            }
        }
        throw new BusinessException("Row doesn't exist");
    }
}

