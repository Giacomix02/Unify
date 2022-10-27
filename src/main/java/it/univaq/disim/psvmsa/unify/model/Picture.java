package it.univaq.disim.psvmsa.unify.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

public class Picture {

    private Integer id;
    private byte[] content;

    public Picture(byte[] content) {
        this.content = content;
    }

    public Picture(byte[] content, Integer id){
        this(content);
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setContent(byte[] content) {
        this.content = content;
    }
    public void setContentFromStream(InputStream content) throws IOException {
        this.content = content.readAllBytes();
    }
    public InputStream toStream()  {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return Arrays.equals(content,picture.content) &&
                Objects.equals(id, picture.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content,id);
    }

}
