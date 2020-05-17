package allaboutecm.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jdk.nashorn.internal.objects.annotations.Property;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@NodeEntity
public class Group extends Entity {

    @Property(name="name")
    private String name;

    @Relationship(type="musicians")
    private Set<Musician> musicians;

    @Relationship(type="albums")
    private Set<Album> albums;

    public Group(String name) {
        this.name = name;

        musicians = Sets.newHashSet();
        albums = Sets.newHashSet();
    }

    public String getName(){ return name;}

    public void setName(String name) {
        notNull(name);
        notBlank(name);

        this.name = name;
    }

    public Set<Musician> getMusicians() {
        return musicians;
    }

    public void setMusicians(Set<Musician> musicians) {
        notNull(musicians);

        if (musicians.size() < 2) {
            throw new IllegalArgumentException("Featured artist list at least has two artist");
        }

        this.musicians = musicians;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        notNull(albums);

        if (albums.size() == 0) {
            throw new IllegalArgumentException("Album list at least has one album");
        }

        this.albums = albums;
    }

}
