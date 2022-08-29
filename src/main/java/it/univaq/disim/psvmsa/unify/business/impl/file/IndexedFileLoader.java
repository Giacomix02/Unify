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
        try{
            boolean created = this.createFileIfNotExists(path);
            if(created) return indexedFile;
            BufferedReader in = new BufferedReader(new FileReader(path));
            String header = in.readLine();

            if(header == null) return indexedFile;
            indexedFile.setId(Integer.parseInt(header));
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

    public void save(IndexedFile file){
        try{
            this.createFileIfNotExists(path);
            BufferedWriter out = new BufferedWriter(new FileWriter(path));
            out.write(Integer.toString(file.getId()));
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
