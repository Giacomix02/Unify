package it.univaq.disim.psvmsa.unify.business.impl.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class IndexedFileLoader {
    private final String path;
    private final String separator;
    private final int ID_POSITION;

    public IndexedFileLoader(String path, String separator) {
        this.path = path;
        this.separator = separator;
        this.ID_POSITION = 0;
    }
    public IndexedFileLoader(String path, String separator, int ID_POSITION) {
        this.path = path;
        this.separator = separator;
        this.ID_POSITION = ID_POSITION;
    }

    public IndexedFile load(){
        IndexedFile indexedFile = new IndexedFile(ID_POSITION);
        try{
            boolean created = this.createFileIfNotExists(path);
            if(created) return indexedFile;
            BufferedReader in = new BufferedReader(new FileReader(path));
            String header = in.readLine();

            if(header == null) return indexedFile;
            indexedFile.setCurrentId(Integer.parseInt(header));
            String l;
            while ((l = in.readLine())!=null){
                IndexedFile.Row row = IndexedFile.Row.fromText(separator,l);
                indexedFile.appendRow(row);
            }
            in.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return indexedFile;
    }
    public IndexedFile.Row getRowById(int id){
        try{
            this.createFileIfNotExists(path);
            BufferedReader in = new BufferedReader(new FileReader(path));
            String l;
            in.readLine(); // skip header
            while ((l = in.readLine())!=null){
                IndexedFile.Row row = IndexedFile.Row.fromText(separator,l);
                if(row.getIntAt(ID_POSITION) == id){
                    in.close();
                    return row;
                }
            }
            in.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
    public IndexedFile.Row deleteRowById(int id){
        IndexedFile.Row found = null;
        try{
            this.createFileIfNotExists(path);
            BufferedReader in = new BufferedReader(new FileReader(path));
            String l;
            StringBuilder out = new StringBuilder();
            String header = in.readLine();
            if(header == null) return null; // file is empty (no header)
            out.append(header);
            while ((l = in.readLine())!=null){
                IndexedFile.Row row = IndexedFile.Row.fromText(separator,l);
                System.out.println("0 ==> "+row);
                if(row.getIntAt(ID_POSITION) != id){
                    out.append("\r");
                    out.append(l);
                    out.append("\r\n");
                    System.out.println("1 ==> "+out.toString());
                    System.out.println("a1");
                }else{
                    System.out.println("2 ==> "+found);
                    found = row;
                    System.out.println("22 ==> "+found);
                    System.out.println("a2");
                }
            }
            in.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(out.toString());
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return found;
    }
    public List<IndexedFile.Row> loadFiltered(Predicate<IndexedFile.Row> predicate){
        List<IndexedFile.Row> filtered = new ArrayList<>();
        try{
            this.createFileIfNotExists(path);
            BufferedReader in = new BufferedReader(new FileReader(path));
            String header = in.readLine();
            if(header == null) return new ArrayList<>();
            String l;
            while ((l = in.readLine())!=null){
                IndexedFile.Row row = IndexedFile.Row.fromText(separator,l);
                if(predicate.test(row)){
                    filtered.add(row);
                }
            }
            in.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return filtered;
    }

    public void deleteRows(Predicate<IndexedFile.Row> predicate){
        IndexedFile indexedFile = this.load();
        indexedFile.setRows(indexedFile.getRows().stream().filter(predicate.negate()).collect(Collectors.toList()));
        this.save(indexedFile);
    }

    public void save(IndexedFile file){
        try{
            this.createFileIfNotExists(path);
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(Integer.toString(file.getCurrentId()));
            out.newLine();
            for(IndexedFile.Row row : file.getRows()){
                out.write(row.toTextRow());
                out.newLine();
            }
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    private boolean createFileIfNotExists(String path) throws IOException{
        File file = new File(path);
        file.getParentFile().mkdirs();
        return file.createNewFile();
    }
}
