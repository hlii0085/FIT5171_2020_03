package allaboutecm.model;

import allaboutecm.dataaccess.neo4j.URLConverter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jdk.nashorn.internal.objects.annotations.Property;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * An artist that has been featured in (at least) one ECM record.
 *
 * See {@https://www.ecmrecords.com/artists/1435045745}
 */

@NodeEntity
public class Musician extends Entity {

    @Property(name="name")
    private String name;

    @Convert(URLConverter.class)
    @Property(name="musicianUrl")
    private URL musicianUrl;

    @Convert(URLConverter.class)
    @Property(name="wikipediaUrl")
    private URL wikipediaUrl;

    @Relationship(type="fanSites")
    private Set<URL> fanSites;

    @Property(name="biography")
    private String biography;

    @Relationship(type="albums")
    private Set<Album> albums;

    @Relationship(type="comments")
    private List<Comment> comments;


    public Musician(String name) {
        notNull(name);
        notBlank(name);
        String[] nameStrings = name.split(" ");
        if(nameStrings.length == 1) {
            throw new IllegalArgumentException("name must contain one blank");
        } else {
            for(String namePart: nameStrings) {
                if(namePart.equals("")){
                    throw new IllegalArgumentException("name must contain one blank");
                }
            }
        }

        this.name = name;
        this.musicianUrl = null;
        this.biography = null;
        this.wikipediaUrl = null;
        fanSites = Sets.newLinkedHashSet();
        albums = Sets.newLinkedHashSet();
        comments = Lists.newArrayList();
    }

    public void setName(String name) {
        notNull(name);
        notBlank(name);
        String[] nameStrings = name.split(" ");
        if(nameStrings.length == 1) {
            throw new IllegalArgumentException("name must contain one blank");
        } else {
            for(String namePart: nameStrings) {
                if(namePart.equals("")){
                    throw new IllegalArgumentException("name must contain one blank");
                }
            }
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        notNull(biography);
        notBlank(biography);

        this.biography = biography;
    }

    public Set<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<Album> albums) {
        notNull(albums);
        this.albums = albums;
    }

    public Set<URL> getFanSites() {
        return fanSites;
    }

    public void setFanSites(Set<URL> fanSites) {
        notNull(fanSites);
        if (fanSites.isEmpty()) {
            throw new IllegalArgumentException("Fan site list has no element");
        }
        this.fanSites = fanSites;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Musician that = (Musician) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public URL getMusicianUrl() {
        return musicianUrl;
    }

    public void setMusicianUrl(String musicianUrl) throws MalformedURLException {
        notNull(musicianUrl);
        URL url = new URL(musicianUrl) ;
        this.musicianUrl = url;
    }

    public URL getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(URL WikipediaUrl) {
        notNull(WikipediaUrl);
        this.musicianUrl = WikipediaUrl;
    }

    public List<Comment> getComment() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        notNull(comments);
        if (comments.isEmpty()) {
            throw new IllegalArgumentException("Comment list contains no comment");
        }
    }
}