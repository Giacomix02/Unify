package it.univaq.disim.psvmsa.unify.model;


import javafx.scene.image.Image;

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
}
