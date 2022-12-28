package it.univaq.disim.psvmsa.unify.view.components;

import it.univaq.disim.psvmsa.unify.model.Song;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class MusicPlayer {


    private List<Consumer<Song>> songConsumers = new ArrayList<>(); // list of event
    private static MusicPlayer instance = new MusicPlayer(); //Singleton
    private static MediaPlayer player;

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

    public void playAlbum() {
        if (queue != null) {
            for (Song song : queue) {
                System.out.println(song.getName());
                play(song);
            }
            queue.clear();
        }

    }

    private void play(Song song){
        try {
            if(player != null){
                player.stop();
                player.setOnEndOfMedia(null);
            }
            if(song == null) return;
            Media media = new Media(createTempFile(song).toUri().toString());
            this.player = new MediaPlayer(media);
            player.setAutoPlay(false);
            player.play();
            player.setOnEndOfMedia(() -> {
                play(queue.poll());
            });
            emitSongPlay(song);
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

    public void addOnSongPlay(Consumer<Song> consumer){
        this.songConsumers.add(consumer);
    }


    private void emitSongPlay(Song song){
        for(Consumer<Song> consumer : songConsumers){
            consumer.accept(song);
        }
    }
}