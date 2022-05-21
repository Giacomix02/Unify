package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.view.Pages;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


public class MenuLink extends HBox {
    private String menuText;
    private Pages href;
    private HBox actionBox;
    private Label label;
    private ImageView image;
    private String imageURL;
    private boolean isActive = false;

    public MenuLink(String text, Pages href, String imageURL) {
        super();
        this.menuText = text;
        this.href = href;
        this.imageURL = imageURL;
        this.init();
    }

    public void setActive(boolean active) {
        this.isActive = active;
        if (active) {
            this.getStyleClass().add("menu-link-active");
        } else {
            this.getStyleClass().remove("menu-link-active");
        }
    }

    private void init() {
        this.getStyleClass().add("menu-link");
        this.setCursor(Cursor.HAND);
        this.image = new ImageView(this.imageURL);
        this.image.setFitWidth(20);
        this.image.setFitHeight(20);
        this.label = new Label(this.menuText);
        this.actionBox = new HBox();
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);
        this.actionBox.fillHeightProperty().set(true);
        this.actionBox.getStyleClass().add("menu-link-accent");
        actionBox.setMinWidth(8);
        actionBox.setMaxWidth(8);
        this.getChildren().addAll(this.actionBox, this.image, this.label);
    }

    public Pages getHref() {
        return this.href;
    }

}
