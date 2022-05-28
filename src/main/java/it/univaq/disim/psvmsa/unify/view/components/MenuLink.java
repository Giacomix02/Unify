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
    private Pages page;
    private HBox actionBox;
    private Label label;
    private ImageView imageView;
    private Image image;
    private boolean isActive = false;

    public MenuLink(String text, Pages page, Image image) {
        super();
        this.menuText = text;
        this.page = page;
        this.image = image;
        this.initialize();
    }

    public void setActive(boolean active) {
        this.isActive = active;
        if (active) {
            this.getStyleClass().add("menu-link-active");
        } else {
            this.getStyleClass().remove("menu-link-active");
        }
    }

    private void initialize() {
        this.getStyleClass().add("menu-link");
        this.setCursor(Cursor.HAND);
        this.imageView = new ImageView(this.image);
        this.imageView.setFitWidth(20);
        this.imageView.setFitHeight(20);
        this.label = new Label(this.menuText);
        this.actionBox = new HBox();
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);
        this.actionBox.fillHeightProperty().set(true);
        this.actionBox.getStyleClass().add("menu-link-accent");
        actionBox.setMinWidth(8);
        actionBox.setMaxWidth(8);
        this.getChildren().addAll(this.actionBox, this.imageView, this.label);
    }

    public Pages getPage() {
        return this.page;
    }

}
