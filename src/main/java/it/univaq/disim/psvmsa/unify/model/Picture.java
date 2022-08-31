package it.univaq.disim.psvmsa.unify.model;


import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class Picture {

    private Integer id;
    private InputStream stream;

    public Picture(InputStream stream) {
        this.stream = stream;
    }

    public Picture(InputStream stream, Integer id){
        this(stream);
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public InputStream getImageStream() {
        return stream;
    }

    public void setImageStream(InputStream stream) {
        this.stream = stream;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return Objects.equals(stream,picture.stream) &&
                Objects.equals(id, picture.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(stream,id);
    }
}
