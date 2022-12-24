package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.business.impl.file.IndexedFile;
import it.univaq.disim.psvmsa.unify.model.Picture;
import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.media.MediaPlayer;

import java.util.Timer;
import java.util.TimerTask;

public class MusicBar extends HBox {
    //TODO maybe make this into a controller and make a static method that creates the music bar and
    //returns the FXML loader to the caller, so that we can get both the component and it's controller
    private static Image playImage = new Image("ui/images/icons/play.png");
    private static Image pauseImage = new Image("ui/images/icons/pause.png");
    private static Label songName;
    private static ImageView songImage;
    private static ImageView playPauseButton;
    private ImageView nextButton;
    private ImageView previousButton;
    private Slider sliderBar;
    private int playId = 0;
    private int songLength = 100;
    private int currentPosition = 0;

    private static Song currentSong;

    private static boolean isPlaying = false;
    MusicPlayer musicPlayer = new MusicPlayer();

    public MusicBar() {
        super();
        init();
    }

    public void play() {
        isPlaying = true;
        playPauseButton.setImage(pauseImage);
        playTick(++playId);
        musicPlayer = musicPlayer.getInstance();
        musicPlayer.resume();
    }

    private void playTick(int id) {

    }

    public void pause() {
        isPlaying = false;
        playId++;
        playPauseButton.setImage(playImage);
        musicPlayer = musicPlayer.getInstance();
        musicPlayer.pause();
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
            previousButton = (ImageView) root.lookup("#previousButton");
            sliderBar = (Slider) root.lookup("#sliderBar");
            playPauseButton.setImage(playImage);
            playPauseButton.setOnMouseClicked(event -> {
                if (isPlaying) {
                    pause();
                } else {
                    play();
                }
            });
            sliderBar.valueProperty().addListener((ov, oldValue, newValue) -> {
                currentPosition = newValue.intValue();
                String style = String.format(
                        "-fx-background-color: linear-gradient(to right, accent %d%%, transparent %d%%);",
                        oldValue.intValue(), newValue.intValue()
                );
                sliderBar.setStyle(style);
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void setButtonImage(boolean change) {
        if (change) {
            playPauseButton.setImage(pauseImage);
            isPlaying = true;
        }
    }

    static public void setSong(Song song) {
        songName.setText(song.getName());
        Image image = new Image(song.getPicture().toStream());
        songImage.setImage(image);
    }
}
