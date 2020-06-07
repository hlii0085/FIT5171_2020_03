package allaboutecm.dataaccess.neo4j;

import allaboutecm.dataaccess.DAO;
import allaboutecm.model.Album;
import allaboutecm.model.Entity;
import allaboutecm.model.MusicalInstrument;
import allaboutecm.model.Musician;
import allaboutecm.model.MusicianInstrument;
import com.google.common.collect.Sets;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.transaction.Transaction;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.net.URL;

import static org.neo4j.ogm.cypher.ComparisonOperator.EQUALS;

public class Neo4jDAO implements DAO {
    private static final int DEPTH_LIST = 0;
    private static final int DEPTH_ENTITY = 1;

    private Session session;

    public Neo4jDAO(Session session) {
        this.session = session;
    }

    // get one object
    @Override
    public <T extends Entity> T load(Class<T> clazz, Long id) {

        return session.load(clazz, id, DEPTH_ENTITY);
    }

    // dao.createOrUpdate()
    @Override
    public <T extends Entity> T createOrUpdate(T entity) {
        Class clazz = entity.getClass();

        T existingEntity = findExistingEntity(entity, clazz);
        if (null != existingEntity) {
            entity.setId(existingEntity.getId());
        }
        Transaction tx = session.beginTransaction();
        session.save(entity, DEPTH_ENTITY);
        tx.commit();
        return entity;

    }


    // dao.loadAll(Musician.class) ==> select * from Musician
    @Override
    public <T extends Entity> Collection<T> loadAll(Class<T> clazz) {
        return session.loadAll(clazz, DEPTH_LIST);


    }

    // delete musician - album
    // dao.delete() ==> delete from
    @Override
    public <T extends Entity> void delete(T entity) {
        Class clazz = entity.getClass();
        T existingEntity = findExistingEntity(entity, clazz);
        if (null == existingEntity) {
            throw new IllegalArgumentException("The entity not exist");
        } else if (clazz.equals(Musician.class)) {
            //Musician musician = (Musician) entity;
            Collection<MusicianInstrument> musicianInstruments = this.loadAll(MusicianInstrument.class);
            for (MusicianInstrument musicianInstrument : musicianInstruments) {
                session.delete(musicianInstrument);
            }
            session.delete(entity);
        }
    }

    @Override
    public Musician findMusicianByName(String name) {
        Filters filters = new Filters();
        filters.add(new Filter("name", EQUALS, name));
        Collection<Musician> musicians = session.loadAll(Musician.class, filters);
        if (musicians.isEmpty()) {
            return null;
        } else {
            return musicians.iterator().next();
        }
    }

    private <T extends Entity> T findExistingEntity(Entity entity, Class clazz) {
        Filters filters = new Filters();
        Collection<? extends Entity> collection = Sets.newLinkedHashSet();
        if (clazz.equals(Album.class)) {
            // Album
            Album album = (Album) entity;
            filters.add(new Filter("albumName", EQUALS, album.getAlbumName())
                    .and(new Filter("recordNumber", EQUALS, album.getRecordNumber()))
                    .and(new Filter("releaseYear", EQUALS, album.getReleaseYear())));
            collection = session.loadAll(Album.class, filters);
        } else if (clazz.equals(Musician.class)) {
            // Musician
            Musician musician = (Musician) entity;
            filters.add(new Filter("name", EQUALS, musician.getName()));
            collection = session.loadAll(Musician.class, filters);
        } else if (clazz.equals(MusicalInstrument.class)) {
            // MusicalInstrument
            MusicalInstrument musicalInstrument = (MusicalInstrument) entity;
            filters.add(new Filter("name", EQUALS, musicalInstrument.getName()));
            collection = session.loadAll(MusicalInstrument.class, filters);
        } else if (clazz.equals(MusicianInstrument.class)) {
            // MusicianInstrument
            MusicianInstrument musicianInstrument = (MusicianInstrument) entity;
            filters.add(new Filter("musician", EQUALS, musicianInstrument.getMusician()))
                    .and(new Filter("musicalInstruments", EQUALS, musicianInstrument.getMusicalInstrument()));
            collection = session.loadAll(MusicianInstrument.class, filters);
        }
        Entity existingEntity = null;
        if (!collection.isEmpty()) {
            existingEntity = collection.iterator().next();
        }
        return (T) existingEntity;
    }

    @Override
    public Album findAlbumByName(String name) {

        if (!(name == null && StringUtils.isEmpty(name) ))
        {
            Collection <Album> albumList = session.loadAll(Album.class);

            for (Album album  : albumList)
            {

                if (album.getAlbumName().equalsIgnoreCase(name))
                {
                    return album;
                }
            }
        }
        return null;
    }

    @Override
    public List<Album> findAlbumsByYear(int year) {
        Collection<Album> albums = session.loadAll(Album.class);
        List<Album> matchedAlbums = new ArrayList<Album>();

        if(year > 0) {
            for (Album a :albums) {
                if(a.getReleaseYear() == year) {
                    matchedAlbums.add(a);
                }
            }
        }
        else {
            throw new IllegalArgumentException("The given year is invalid");
        }
        return matchedAlbums;
    }

    @Override
    public Musician findMusicianByURL(URL url) {
        Filters filters = new Filters();
        filters.add(new Filter("url", EQUALS, url));
        Collection<Musician> musicians = session.loadAll(Musician.class, filters);
        if (musicians.isEmpty()) {
            return null;
        } else {
            return musicians.iterator().next();
        }
    }

    @Override
    public Album findAlbumByRecordNumber(String recordNumber) {
        Filters filters = new Filters();
        filters.add(new Filter("recordNumber", EQUALS, recordNumber));
        Collection<Album> albums = session.loadAll(Album.class, filters);
        if (albums.isEmpty()) {
            return null;
        } else {
            return albums.iterator().next();
        }
    }

    @Override
    public Album findAlbumByURL(URL albumURL) {
        Filters filters = new Filters();
        filters.add(new Filter("albumURL", EQUALS, albumURL));
        Collection<Album> albums = session.loadAll(Album.class, filters);
        if (albums.isEmpty()) {
            return null;
        } else {
            return albums.iterator().next();
        }
    }
}
