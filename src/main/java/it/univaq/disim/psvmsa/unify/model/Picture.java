package it.univaq.disim.psvmsa.unify.model;


public class Picture {

    private Integer id;
    private String url;

    public Picture(String url) {
        this.url = url;
    }

    public Picture(String url, Integer id){
        this(url);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
