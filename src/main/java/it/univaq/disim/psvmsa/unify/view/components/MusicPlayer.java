package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.beans.property.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MusicPlayer {
    private SimpleObjectProperty<Song> currentSong = new SimpleObjectProperty<>();
    private SimpleDoubleProperty playbackPosition = new SimpleDoubleProperty();
    private SimpleBooleanProperty isPlaying = new SimpleBooleanProperty();
    private static MusicPlayer instance = new MusicPlayer(); //Singleton
    private  MediaPlayer player;

    private LinkedList<Song> queue = new LinkedList<>();

    public MusicPlayer() {
    }

    public void enqueue(Song song) {
        queue.add(song);
    }

    public void enqueue(List<Song> songs) {
        queue.addAll(songs);
    }

    public void playOne(Song song){
        queue.clear();
        play(song);
    }
    public void setQueue(List<Song> songs){
        queue.clear();
        queue.addAll(songs);
    }

    public void startQueuePlayback(){
        if(queue.size() > 0){
            play(queue.pop());
        }
    }
    private void play(Song song){
        try {
            if(player != null){
                player.stop();
                player.setOnEndOfMedia(null);
            }
            if(song == null) {
                isPlaying.set(false);
                return;
            }

            Media media = new Media(createTempFile(song).toUri().toString());
            this.player = new MediaPlayer(media);
            player.setAutoPlay(false);
            player.play();
            isPlaying.set(true);
            player.currentTimeProperty().addListener(ov -> {
                    double total = player.getTotalDuration().toMillis();
                    double current = player.getCurrentTime().toMillis();
                    double progress = (double) (current / total * 100);
                    playbackPosition.set(progress);
            });
            player.setOnEndOfMedia(() -> {
                play(queue.poll());
            });
            currentSong.set(song);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean next(){
        if(queue.size() > 0){
            play(queue.poll());
            return true;
        }
        return false;
    }

    private Path createTempFile(Song song) throws IOException {
        Path path = Files.createTempFile(song.getId().toString(), ".mp3");
        Files.write(path, song.toStream().readAllBytes());
        return path;
    }

    public void pause(){
        player.pause();
        isPlaying.set(false);
    }

    public void resume(){
        player.play();
        isPlaying.set(true);
    }

    public void toggleResume(){
        if(player.getStatus().equals(MediaPlayer.Status.PLAYING)){
            pause();
        }else{
            resume();
        }
    }

    public MusicPlayer getInstance() {
        return instance;
    }

    public SimpleDoubleProperty playbackPositionProperty() {
        return playbackPosition;
    }
    public SimpleObjectProperty<Song> currentSongProperty() {
        return currentSong;
    }
    public SimpleBooleanProperty isPlayingProperty() {
        return isPlaying;
    }
}