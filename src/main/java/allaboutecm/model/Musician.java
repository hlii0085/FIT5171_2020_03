package allaboutecm.model;

import com.google.common.collect.Sets;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * An artist that has been featured in (at least) one ECM record.
 *
 * See {@https://www.ecmrecords.com/artists/1435045745}
 */
public class Musician extends Entity {
    private String  name;

    private URL musicianUrl;

    private URL wikipediaUrl;

    private Set<URL> fanSites;

    private String biography;

    private Set<Album> albums;


    public Musician(String name) {
        this.name = name;
        this.musicianUrl = null;
        this.biography = null;
        this.wikipediaUrl = null;
        fanSites = Sets.newLinkedHashSet();
        albums = Sets.newLinkedHashSet();
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
        for (Album album : albums){
            for (Album alb : albums){
                if (album.equals(alb)){
                    throw new IllegalArgumentException("There are same");
                }
            }
        }
        this.albums = albums;
    }

    public Set<URL> getFanSites() {
        return fanSites;
    }

    public void setFanSites(Set<URL> fanSites) {
        notNull(fanSites);
        if (fanSites.size() == 0) {
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
        URL url = new URL(musicianUrl);
        this.musicianUrl = url;
    }

    public URL getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(URL WikipediaUrl) {
        notNull(WikipediaUrl);
        this.musicianUrl = WikipediaUrl;
    }
}