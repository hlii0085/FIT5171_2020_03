package allaboutecm.dataaccess;

import allaboutecm.model.Entity;
import allaboutecm.model.Musician;

import java.util.Collection;

public interface DAO {
    <T extends Entity> T load(Class<T> clazz, Long id);

    <T extends Entity> T createOrUpdate(T entity);

    <T extends Entity> Collection<T> loadAll(Class<T> clazz);

    <T extends Entity> void delete(T entity);

    Musician findMusicianByName(String name);
}
