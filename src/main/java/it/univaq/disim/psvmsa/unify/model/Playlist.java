package it.univaq.disim.psvmsa.unify.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Playlist {

    private String name;

    private Integer id;

    private List<Song> songs = new ArrayList<Song>();

    private User user;

    public Playlist(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Playlist(String name, User user, Integer id) {
        this(name, user);
        this.id = id;
    }

    public Playlist(String name, User user, Integer id, List<Song> songs) {
        this(name, user, id);
        this.songs = songs;
    }

    public boolean hasSong(Song song) {
        return songs.contains(song);
    }

    public void addSong(Song song) throws SongAlreadyExistsException {
        if (hasSong(song)) throw new SongAlreadyExistsException(song.getName() + " already added");
        songs.add(song);
    }

    public void removeSong(Song song) {
        songs.remove(song);
    }

    public Song getSong(int index) {
        return songs.get(index);
    }

    public Song getRandomSong() {
        if (songs.size() == 0) return null;
        Random random = new Random();
        return getSong(random.nextInt(songs.size()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Playlist)) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(user, playlist.user) &&
                Objects.equals(name, playlist.name) &&
                Objects.equals(id, playlist.id) &&
                Objects.equals(songs, playlist.songs);
    }
    @Override
    public int hashCode() {
        return Objects.hash(user, name, id, songs);
    }

}
