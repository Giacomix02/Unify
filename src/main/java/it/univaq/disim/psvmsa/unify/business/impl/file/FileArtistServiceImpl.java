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
        public static int ARTIST_TYPE = 3;
    }
    private static class GroupSchema {
        public static int RELATION_ID = 0;
        public static int GROUP_ID = 1;
        public static int ARTIST_ID = 2;
    }
    private static class ImagesSchema {
        public static int RELATION_ID = 0;
        public static int ARTIST_ID = 1;
        public static int IMAGE_ID = 2;
    }
    private final String SEPARATOR = "|";
    private final IndexedFileLoader loader;
    private final IndexedFileLoader groupRelationsLoader;

    private final IndexedFileLoader imagesRelationsLoader;
    private final PictureService pictureService;


    public FileArtistServiceImpl(String path, String groupPath, String picturesPath ,PictureService pictureService) {
        this.loader = new IndexedFileLoader(path, this.SEPARATOR, Schema.ARTIST_ID);
        this.groupRelationsLoader = new IndexedFileLoader(groupPath, this.SEPARATOR, GroupSchema.RELATION_ID);
        this.imagesRelationsLoader = new IndexedFileLoader(picturesPath, this.SEPARATOR, ImagesSchema.RELATION_ID);
        this.pictureService = pictureService;
    }

    @Override
    public List<Artist> getArtists(){
        List<Artist> artists = new ArrayList<>();
        IndexedFile file = loader.load();
        List<IndexedFile.Row> rows = file.getRows();
        IndexedFile groupFile = groupRelationsLoader.load();
        for (IndexedFile.Row row : rows) {
            artists.add(parseArtist(row, groupFile));
        }
        return artists;
    }

    @Override
    public Artist getById(Integer id) {
        return getById(id, groupRelationsLoader.load());
    }
    //to prevent reloading of group relations everytime
    private Artist getById(Integer id, IndexedFile relations){
        IndexedFile.Row row = loader.getRowById(id);
        if (row == null) return null;
        return parseArtist(row, relations);
    }

    @Override
    public void deleteById(Integer id) throws BusinessException {
        IndexedFile.Row deleted = loader.deleteRowById(id);
        if(deleted == null) throw new BusinessException("Artist not found");
        Artist parsed = parseArtist(deleted, groupRelationsLoader.load());
        if(parsed instanceof GroupArtist) removeGroupRelations((GroupArtist) parsed);
        deleteImagesRelations(parsed);
    }

    //for performance reasons i pass the reference to the relations else it would cause the file to be
    //re read everytime
    private Artist parseArtist(IndexedFile.Row row, IndexedFile relations){
        ArtistType kind = ArtistType.fromInt(row.getIntAt(Schema.ARTIST_TYPE));
        List<Picture> pictures = getArtistPicturesFromId(row.getIntAt(Schema.ARTIST_ID));
        if(kind == ArtistType.Single){
            return new Artist(
                    row.getStringAt(Schema.ARTIST_NAME),
                    row.getStringAt(Schema.ARTIST_BIOGRAPHY),
                    pictures,
                    row.getIntAt(Schema.ARTIST_ID)
            );
        }else if(kind == ArtistType.Group){
            List<Artist> members = new ArrayList<>();
            List<IndexedFile.Row> membersRows = relations.filterRows(
                    relationRow -> relationRow.getIntAt(GroupSchema.GROUP_ID) == row.getIntAt(Schema.ARTIST_ID)
            );
            //recursively parses each member
            for(IndexedFile.Row memberRow: membersRows){
                members.add(getById(memberRow.getIntAt(GroupSchema.ARTIST_ID), relations));
            }
            return new GroupArtist(
                    row.getStringAt(Schema.ARTIST_NAME),
                    row.getStringAt(Schema.ARTIST_BIOGRAPHY),
                    pictures,
                    members,
                    row.getIntAt(Schema.ARTIST_ID)
            );
        }else {
            throw new RuntimeException("Invalid artist kind");
        }
    }

    private List<Picture> getArtistPicturesFromId(int id){
        List<IndexedFile.Row> images = imagesRelationsLoader.loadFiltered(row -> row.getIntAt(ImagesSchema.ARTIST_ID) == id);
        List<Picture> pictures = new ArrayList<>();
        for(IndexedFile.Row row: images){
            pictures.add(pictureService.getById(row.getIntAt(ImagesSchema.IMAGE_ID)));
        }
        return pictures;
    }
    private void addImagesRelations(Artist artist){
        IndexedFile relationFile = imagesRelationsLoader.load();
        for(Picture picture: artist.getPictures()){
            Picture p = pictureService.add(picture);
            picture = p == null ? picture : p; //if the picture already exists it will return null
            IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
            row.set(ImagesSchema.RELATION_ID, relationFile.incrementId())
                    .set(ImagesSchema.IMAGE_ID, picture.getId())
                    .set(ImagesSchema.ARTIST_ID, artist.getId());
            relationFile.appendRow(row);
        }
        imagesRelationsLoader.save(relationFile);
    }

    private void deleteImagesRelations(Artist artist) throws BusinessException{
        IndexedFile relationFile = imagesRelationsLoader.load();
        List<IndexedFile.Row> rows = relationFile.filterRows(
                r -> r.getIntAt(ImagesSchema.ARTIST_ID) == artist.getId()
        );
        for (IndexedFile.Row row : rows) {
            relationFile.deleteRowById(row.getIntAt(ImagesSchema.RELATION_ID));
        }
        imagesRelationsLoader.save(relationFile);
    }
    @Override
    public Artist add(Artist artist) {
        if(existsArtist(artist)) return null;
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
        int id = file.incrementId();
        artist.setId(id);
        row.set(Schema.ARTIST_ID, artist.getId())
                .set(Schema.ARTIST_NAME, artist.getName())
                .set(Schema.ARTIST_BIOGRAPHY, artist.getBiography());
        addImagesRelations(artist);
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
    private boolean existsArtist(Artist artist){
        if(artist.getId() == null) return false;
        return loader.getRowById(artist.getId()) != null;
    }
    @Override
    public void update(Artist artist) throws BusinessException {
        IndexedFile file = loader.load();
        IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);

        row.set(Schema.ARTIST_ID, artist.getId())
                .set(Schema.ARTIST_NAME, artist.getName())
                .set(Schema.ARTIST_BIOGRAPHY, artist.getBiography());
        deleteImagesRelations(artist);
        addImagesRelations(artist);
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
        String name = n.toLowerCase();
        List<IndexedFile.Row> rows = loader.loadFiltered(
                r -> r.getStringAt(Schema.ARTIST_NAME).toLowerCase().contains(name)
        );
        List<Artist> artists = new ArrayList<>();
        for (IndexedFile.Row row : rows) {
            artists.add(getById(row.getIntAt(Schema.ARTIST_ID)));
        }
        return artists;
    }
    public void addGroupRelations(GroupArtist group){
        IndexedFile file = groupRelationsLoader.load();
        for(Artist member: group.getArtists()){
                if(!existsArtist(member)){
                    Artist m = add(member);
                    member = m == null ? member : m; //if the artist already exists it will return null
                }
            IndexedFile.Row row = new IndexedFile.Row(this.SEPARATOR);
            int id = file.incrementId();
            row.set(GroupSchema.RELATION_ID, id)
                    .set(GroupSchema.GROUP_ID, group.getId())
                    .set(GroupSchema.ARTIST_ID, member.getId());
            file.appendRow(row);
        }
        groupRelationsLoader.save(file);
    }

    private void removeGroupRelations(GroupArtist group) throws BusinessException{
        IndexedFile file = groupRelationsLoader.load();
        List<IndexedFile.Row> members = file.filterRows(
                relationRow -> relationRow.getIntAt(GroupSchema.GROUP_ID) == group.getId()
        );
        for(IndexedFile.Row r : members){
            file.deleteRowById(r.getIntAt(GroupSchema.RELATION_ID));
        }
        groupRelationsLoader.save(file);
    }
}
