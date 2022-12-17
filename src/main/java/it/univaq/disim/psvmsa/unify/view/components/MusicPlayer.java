package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class MusicPlayer {

    private MusicPlayer instance = new MusicPlayer();
    private MediaPlayer player;
    LinkedList<Song> queue = new LinkedList<>();

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
        queue.add(song);
    }
    private void play(Song song){
        try {
            if(player != null){
                player.stop();
                player.setOnEndOfMedia(null);
            }
            if(song == null) return;
            Media media = new Media(createTempFile(song).toString());
            player = new MediaPlayer(media);
            player.play();
            player.setOnEndOfMedia(() -> {
                play(queue.poll());
            });
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
        return Files.createTempFile(song.getId().toString(), ".mp3");
    }
    public void pause(){
        player.pause();
    }
    public void resume(){
        player.play();
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
}
