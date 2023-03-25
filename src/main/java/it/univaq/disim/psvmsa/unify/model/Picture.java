package it.univaq.disim.psvmsa.unify.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

public class Picture {

    private Integer id;
    private InputStream content;
    private byte[] cache;

    public Picture(InputStream content) {
        this.content = content;
    }

    public Picture(InputStream content, Integer id){
        this(content);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(InputStream content) {
        this.content = content;
        cache = null;
    }

    public InputStream toStream()  {
        if (cache == null) {
            try {
                cache = content.readAllBytes();
            } catch (IOException e) {
                //TODO should i throw an exception?
                e.printStackTrace();
            }
        }
        return new ByteArrayInputStream(this.cache);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return Objects.equals(id, picture.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content,id);
    }

}
