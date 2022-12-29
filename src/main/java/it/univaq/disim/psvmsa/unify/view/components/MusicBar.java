package it.univaq.disim.psvmsa.unify.view.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class MusicBar extends HBox {
    //TODO maybe make this into a controller and make a static method that creates the music bar and
    //returns the FXML loader to the caller, so that we can get both the component and it's controller
    private static Image playImage = new Image("ui/images/icons/play.png");
    private static Image pauseImage = new Image("ui/images/icons/pause.png");
    private static Label songName;
    private static ImageView songImage;
    private static ImageView playPauseButton;
    private ImageView nextButton;
    private Slider sliderBar;
    MusicPlayer musicPlayer;
    private boolean isChangingTime = false;
    public MusicBar(MusicPlayer musicPlayer) {
        super();
        this.musicPlayer = musicPlayer;
        init();
    }


    private void init() {
        try {
            this.setStyle("-fx-padding: 10");
            HBox root = FXMLLoader.load(getClass().getResource("/ui/components/MusicBar.fxml"));
            HBox.setHgrow(root, Priority.ALWAYS);
            getChildren().add(root);
            songName = (Label) root.lookup("#songName");
            songImage = (ImageView) root.lookup("#songImage");
            playPauseButton = (ImageView) root.lookup("#playButton");
            nextButton = (ImageView) root.lookup("#nextButton");
            sliderBar = (Slider) root.lookup("#sliderBar");
            playPauseButton.setImage(playImage);
            playPauseButton.setOnMouseClicked(event -> {
                musicPlayer.toggleResume();
            });
            nextButton.setOnMouseClicked(event -> {
                musicPlayer.next();
            });
            musicPlayer.currentSongProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    songName.setText(newValue.getName());
                    Image image = new Image(newValue.getPicture().toStream());
                    songImage.setImage(image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            musicPlayer.isPlayingProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    playPauseButton.setImage(pauseImage);
                } else {
                    playPauseButton.setImage(playImage);
                }
            });
            sliderBar.setOnMouseReleased(event -> {
                isChangingTime = false;
                musicPlayer.seekPercentage(sliderBar.getValue());
            });
            sliderBar.setOnMousePressed(event -> {
                isChangingTime = true;
            });
            musicPlayer.playbackPositionProperty().addListener((observable, oldValue, newValue) -> {
                if (!this.isChangingTime) {
                    sliderBar.setValue(newValue.doubleValue());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}