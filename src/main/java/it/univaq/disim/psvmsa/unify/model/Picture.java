package it.univaq.disim.psvmsa.unify.model;


import javafx.scene.image.Image;

import java.util.Objects;

public class Picture {

    private Integer id;
    private Image image;

    public Picture(Image image) {
        this.image = image;
    }

    public Picture(Image image, Integer id){
        this(image);
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Picture)) return false;
        Picture picture = (Picture) o;
        return Objects.equals(image,picture.image) &&
                Objects.equals(id, picture.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(image,id);
    }
}
