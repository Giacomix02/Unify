package it.univaq.disim.psvmsa.unify.business.impl.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IndexedFileLoader {
    private String path;
    private String separator;
    public IndexedFileLoader(String path, String separator) {
        this.path = path;
        this.separator = separator;
    }

    public IndexedFile load(){
        IndexedFile indexedFile = new IndexedFile();
        try(BufferedReader in = new BufferedReader(new FileReader(path))){
            String l;
            indexedFile.setId(Integer.parseInt(in.readLine()));
            while ((l = in.readLine())!=null){
                IndexedFile.Row row = IndexedFile.Row.fromText(separator,l);
                indexedFile.appendRow(row);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return indexedFile;
    }

    public void save(IndexedFile file){

    }

}
