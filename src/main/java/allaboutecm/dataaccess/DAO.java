package allaboutecm.dataaccess;

import allaboutecm.model.Album;
import allaboutecm.model.Entity;
import allaboutecm.model.Musician;

import java.net.URL;
import java.util.Collection;
import java.util.List;

public interface DAO {
    <T extends Entity> T load(Class<T> clazz, Long id);

    <T extends Entity> T createOrUpdate(T entity);

    <T extends Entity> Collection<T> loadAll(Class<T> clazz);

    <T extends Entity> void delete(T entity);

    Musician findMusicianByName(String name);

    List<Album> findAlbumsByYear(int year);

    Musician findMusicianByURL(URL url);

    Album findAlbumByName(String name);


    Album findAlbumByRecordNumber(String recordNumber);
    Album findAlbumByURL(URL albumURL);
}
