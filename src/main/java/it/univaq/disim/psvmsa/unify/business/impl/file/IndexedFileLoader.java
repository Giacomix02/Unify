package it.univaq.disim.psvmsa.unify.business.impl.file;

import java.io.*;

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

            in.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return indexedFile;
    }

    public void save(IndexedFile file){
        try(FileWriter out = new FileWriter(path)){
            out.flush();
            out.write(file.getId());
            for(IndexedFile.Row row : file.getRows()){
                out.write(row.toTextRow());
            }
            out.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public void create(String name){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(name)))){}
        catch (IOException e){
            e.printStackTrace();
        }
    }



}
