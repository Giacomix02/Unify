package it.univaq.disim.psvmsa.unify.business.impl.ram;

import it.univaq.disim.psvmsa.unify.business.*;
import it.univaq.disim.psvmsa.unify.model.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RAMUnifyServiceFactory extends UnifyServiceFactory {
    private RAMGenreServiceImpl ramGenreService = new RAMGenreServiceImpl();
    private RAMPictureServiceImpl ramPictureService = new RAMPictureServiceImpl();
    private RAMSongServiceImpl ramSongService = new RAMSongServiceImpl();
    private RAMPlaylistServiceImpl ramPlaylistService = new RAMPlaylistServiceImpl(ramSongService);
    private RAMUserServiceImpl ramUserService = new RAMUserServiceImpl();
    private  RAMAlbumServiceImpl ramAlbumService = new RAMAlbumServiceImpl(ramSongService);
    private  RAMArtistServiceImpl ramArtistService = new RAMArtistServiceImpl();

    public RAMUnifyServiceFactory(){
        try{
            User adminUser = new Admin("admin","admin");
            User user = new User("user","user");
            ramUserService.add(user);
            ramUserService.add(adminUser);

            Genre rock = new Genre(1,"Rock");
            Genre electronicPop = new Genre(2,"Electronic Pop");
            ramGenreService.add(rock);
            ramGenreService.add(electronicPop);

            List<Picture> alanWalkerPictures = new ArrayList<>();
            alanWalkerPictures.add(new Picture(new FileInputStream("data/images/4.png")));
            Artist alanWalker = new Artist("Alan Walker", "Alan Olav Walker is a British-Norwegian music producer and DJ.", alanWalkerPictures);
            ramArtistService.add(alanWalker);

            Song alone = new Song("Alan Walker & Ava Max - Alone, Pt.II", alanWalker, "We were young, posters on the wall" +
                    "\\nPraying we're the ones that the teacher wouldn't call" +
                    "\\nWe would stare at each other\\n'Cause we were always in trouble" +
                    "\\nAnd all the cool kids did their own thing\\nI was on the outside, always looking in" +
                    "\\nYeah, I was there but I wasn't\\nThey never really cared if I was in" +
                    "\\nWe all need that someone\\nWho gets you like no one else" +
                    "\\nRight when you need it the most" +
                    "\\nWe all need a soul to rely on" +
                    "\\nA shoulder to cry on" +
                    "\\nA friend through the highs and the lows\\nI'm not gonna make it alone" +
                    "\\nLa-la-la-la-la la\\nLa-la-la-la-la 'lone" +
                    "\\nI'm not gonna make it alone" +
                    "\\nLa-la-la-la-la la\\nLa-la-la-la-la 'lone" +
                    "\\nThen I saw your face, your forgiving eyes" +
                    "\\nLooking back at me from the other side" +
                    "\\nLike you understood me" +
                    "\\nAnd I'm never letting you go, oh" +
                    "\\nWe all need that someone" +
                    "\\nWho gets you like no one else\\nRight when you need it the most" +
                    "\\nWe all need a soul to rely on\\nA shoulder to cry on\\nA friend through the highs and the lows" +
                    "\\nI'm not gonna make it alone\\nLa-la-la-la-la la\\nLa-la-la-la-la 'lone\\nI'm not gonna make it alone" +
                    "\\nLa-la-la-la-la la\\nLa-la-la-la-la 'lone\\nI'm not gonna make it alone\\nLa-la-la-la-la la\\nLa-la-la-la-la 'lone" +
                    "\\nI'm not gonna make it alone\\nLa-la-la-la-la la\\nLa-la-la-la-la 'lone\\nI'm not gonna make it alone\\nLa-la-la-la-la-la 'lone" +
                    "\\nI'm not gonna make it alone\\nLa-la-la-la-la la\\nLa-la-la-la-la 'lone" +
                    "\\n'Cause you are that someone\\nThat gets me like no one else\\nRight when I need it the most" +
                    "\\nAnd I'll be the one you rely on\\nA shoulder to cry on\\nA friend through the highs and the lows" +
                    "\\nI'm not gonna make it alone\\nLa-la-la-la-la la\\nLa-la-la-la-la-la 'lone\\nI'm not gonna make it alone" +
                    "\\nLa-la-la-la-la la\\nLa-la-la-la-la-la 'lone\\nI'm not gonna make it alone\\nLa-la-la-la-la la\\nLa-la-la-la-la-la 'lone" +
                    "\\nI'm not gonna make it alone\\nLa-la-la-la-la la\\nLa-la-la-la-la-la 'lone\\nI'm not gonna make it alone",
                    new Picture(new FileInputStream("data/images/7.png")), electronicPop, new FileInputStream("data/songs/5.mp3"));

            Song time = new Song("Hans Zimmer & Alan Walker - Time (Official Remix)", alanWalker, "No lyrics available!",
                    new Picture(new FileInputStream("data/images/5.png")), electronicPop, new FileInputStream("data/songs/3.mp3"));

            ramSongService.add(alone);
            ramSongService.add(time);

            List<Song> worldOfWalkerSongs = new ArrayList<>();
            worldOfWalkerSongs.add(alone);
            worldOfWalkerSongs.add(time);
            Album worldOfWalker = new Album("World of Walker", worldOfWalkerSongs, alanWalker, electronicPop);
            ramAlbumService.add(worldOfWalker);

            Playlist myAdminPlaylist = new Playlist("My Playlist", adminUser);
            Playlist myUserPlaylist = new Playlist("My first playlist", user);

            myAdminPlaylist.addSong(alone);
            myUserPlaylist.addSong(alone);
            myUserPlaylist.addSong(time);

            ramPlaylistService.add(myAdminPlaylist);
            ramPlaylistService.add(myUserPlaylist);

        } catch(BusinessException | FileNotFoundException | SongAlreadyExistsException e){
            e.printStackTrace();
        }
    }

    @Override
    public AlbumService getAlbumService(){
        return ramAlbumService;
    }

    @Override
    public ArtistService getArtistService() {
        return ramArtistService;
    }

    @Override
    public GenreService getGenreService(){
        return ramGenreService;
    }

    @Override
    public PlaylistService getPlaylistService(){
        return ramPlaylistService;
    }

    @Override
    public PictureService getPictureService(){
        return ramPictureService;
    }

    @Override
    public SongService getSongService() {
        return ramSongService;
    }

    @Override
    public UserService getUserService(){
        return ramUserService;
    }
}
