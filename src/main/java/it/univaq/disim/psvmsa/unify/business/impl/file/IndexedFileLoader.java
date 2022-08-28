package it.univaq.disim.psvmsa.unify.business.impl.file;

public class IndexedFileLoader {
    private String path;
    private String separator;
    public IndexedFileLoader(String path, String separator) {
        this.path = path;
        this.separator = separator;
    }

    public IndexedFile load(){
        return new IndexedFile();
    }

    public void save(IndexedFile file){

    }

}
