package it.univaq.disim.psvmsa.unify.business.impl.file;

import it.univaq.disim.psvmsa.unify.business.ArtistService;
import it.univaq.disim.psvmsa.unify.business.BusinessException;
import it.univaq.disim.psvmsa.unify.business.PictureService;
import it.univaq.disim.psvmsa.unify.model.*;

import java.util.ArrayList;
import java.util.List;

public class FileArtistServiceImpl implements ArtistService {
    private enum ArtistType {
        Single(0),
        Group(1);

        private final int id;

        ArtistType(int id) {
            this.id = id;
        }

        public int toInt () {
            return id;
        }

        public static ArtistType fromInt (int id) {
            if (id == Single.id) return Single;
            if (id == Group.id) return Group;
            throw new RuntimeException("Invalid artist kind");
        }
    }
    private static class Schema {
        public static int ARTIST_ID = 0;
        public static int ARTIST_NAME = 1;
        public static int ARTIST_BIOGRAPHY = 2;
        public static int PICTURE_ID = 3;
        public static int ARTIST_TYPE = 4;
    }

    private static class GroupSchema {
        public static int RELATION_ID = 0;
        public static int GROUP_ID = 1;
        public static int ARTIST_ID = 2;
    }

    private final String SEPARATOR = "|";
    private final IndexedFileLoader loader;
    private final IndexedFileLoader groupLoader;
    private final PictureService pictureService;


    public FileArtistServiceImpl(String path, String groupPath, PictureService pictureService) {
        this.loader = new IndexedFileLoader(path, this.SEPARATOR, Schema.ARTIST_ID);
        this.groupLoader = new IndexedFileLoader(groupPath, this.SEPARATOR, GroupSchema.RELATION_ID);
        this.pictureService = pictureService;
    }

    @Override
    public List<Artist> getArtists(){
        List<Artist> artists = new ArrayList<>();
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.getRows();
        IndexedFile groupFile = groupLoader.load();
        for (IndexedFile.Row row : rows) {
            artists.add(parseArtist(row, groupFile));

        }
        return artists;
    }

    @Override
    public Artist getById(Integer id) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = file.findRowById(id);
        if (row == null) return null;
        return parseArtist(row, groupLoader.load());
    }

    @Override
    public void deleteById(Integer id) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row deleted = file.deleteRowById(id);
        Artist parsed = parseArtist(deleted, groupLoader.load());
        if(parsed instanceof GroupArtist) removeGroupRelations((GroupArtist) parsed);
        loader.save(file);
    }

    //for performance reasons i pass the reference to the relations else it would cause the file to be
    //re read everytime
    private Artist parseArtist(IndexedFile.Row row, IndexedFile relations){
        ArtistType kind = ArtistType.fromInt(row.getIntAt(Schema.ARTIST_TYPE));
        if(kind == ArtistType.Single){
            return new Artist(
                    row.getStringAt(Schema.ARTIST_NAME),
                    row.getStringAt(Schema.ARTIST_BIOGRAPHY),
                    pictureService.getById(row.getIntAt(Schema.PICTURE_ID)),
                    row.getIntAt(Schema.ARTIST_ID)
            );
        }else if(kind == ArtistType.Group){
            List<Artist> members = new ArrayList<>();
            List<IndexedFile.Row> membersRows = relations.filterRows(
                    relationRow -> relationRow.getIntAt(GroupSchema.GROUP_ID) == row.getIntAt(Schema.ARTIST_ID)
            );
            //recursively parses each member
            for(IndexedFile.Row memberRow: membersRows){
                members.add(parseArtist(memberRow, relations));
            }
            return new GroupArtist(
                    row.getStringAt(Schema.ARTIST_NAME),
                    row.getStringAt(Schema.ARTIST_BIOGRAPHY),
                    pictureService.getById(row.getIntAt(Schema.PICTURE_ID)),
                    members,
                    row.getIntAt(Schema.ARTIST_ID)
            );
        }else {
            throw new RuntimeException("Invalid artist kind");
        }
    }
    @Override
    public Artist add(Artist artist) {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        int id = file.incrementId();
        artist.setId(id);
        row.set(Schema.ARTIST_ID, artist.getId())
                .set(Schema.ARTIST_NAME, artist.getName())
                .set(Schema.ARTIST_BIOGRAPHY, artist.getBiography())
                .set(Schema.PICTURE_ID, artist.getPicture().getId());
        if(artist instanceof GroupArtist){
            GroupArtist group = (GroupArtist) artist;
            row.set(Schema.ARTIST_TYPE, ArtistType.Group.toInt());
            addGroupRelations(group);
        }else{
            row.set(Schema.ARTIST_TYPE, ArtistType.Single.toInt());
        }
        file.appendRow(row);
        loader.save(file);
        return artist;
    }

    @Override
    public void update(Artist artist) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        row.set(Schema.ARTIST_ID, artist.getId())
                .set(Schema.ARTIST_NAME, artist.getName())
                .set(Schema.ARTIST_BIOGRAPHY, artist.getBiography())
                .set(Schema.PICTURE_ID, artist.getPicture().getId());
        if(artist instanceof GroupArtist){
            GroupArtist group = (GroupArtist) artist;
            removeGroupRelations(group);
            row.set(Schema.ARTIST_TYPE, ArtistType.Group.toInt());
            addGroupRelations(group);
        }else{
            row.set(Schema.ARTIST_TYPE, ArtistType.Single.toInt());
        }
        file.updateRow(row);
        loader.save(file);
    }

    @Override
    public void delete(Artist artist) throws BusinessException {
        deleteById(artist.getId());
    }

    @Override
    public List<Artist> searchArtistsByName(String n) {
        IndexedFile file = loader.load();
        String name = n.toLowerCase();
        List<IndexedFile.Row> rows = file.filterRows(
                r -> r.getStringAt(Schema.ARTIST_NAME).toLowerCase().contains(name)
        );
        List<Artist> artists = new ArrayList<>();
        for (IndexedFile.Row row : rows) {
            artists.add(getById(row.getIntAt(Schema.ARTIST_ID)));
        }
        return artists;
    }

    public void addGroupRelations(GroupArtist group){
        IndexedFile file = groupLoader.load();
        for(Artist member: group.getArtists()){
            IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
            int id = file.incrementId();
            row.set(GroupSchema.RELATION_ID, id)
                    .set(GroupSchema.GROUP_ID, group.getId())
                    .set(GroupSchema.ARTIST_ID, member.getId());
            file.appendRow(row);
        }
        groupLoader.save(file);
    }

    private void removeGroupRelations(GroupArtist group) throws BusinessException{
        IndexedFile file = groupLoader.load();
        List<IndexedFile.Row> members = file.filterRows(
                relationRow -> relationRow.getIntAt(GroupSchema.GROUP_ID) == group.getId()
        );
        for(IndexedFile.Row r : members){
            file.deleteRowById(r.getIntAt(GroupSchema.GROUP_ID));
        }
        groupLoader.save(file);
    }
}
