package it.univaq.disim.psvmsa.unify.view.components;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class RoundedImage extends ImageView {

    public RoundedImage (Image image, double radius, double width, double height) {
        super(image);
        Rectangle rectangle = new Rectangle(0, 0, width, height);
        rectangle.setArcWidth(radius);
        rectangle.setArcHeight(radius);
        ImagePattern pattern = new ImagePattern(image);
        rectangle.setFill(pattern);
        setClip(rectangle);
        setImage(image);
    }
    public RoundedImage (Image image, double radius, double size) {
        this(image, radius, size, size);
    }
}
