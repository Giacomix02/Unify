package it.univaq.disim.psvmsa.unify.business;

import it.univaq.disim.psvmsa.unify.model.Playlist;
import it.univaq.disim.psvmsa.unify.model.Song;
import it.univaq.disim.psvmsa.unify.model.User;

import java.util.List;

public interface PlaylistService {
        List<Playlist> getPlaylistsByUser(User user);
        Playlist getPlaylistById(Integer id);
        int createPlaylist(String name, User user);
        void deletePlaylist(Playlist playlist);
        void updatePlaylist(Playlist playlist);
}
