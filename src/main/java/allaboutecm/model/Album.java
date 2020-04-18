package allaboutecm.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * Represents an album released by ECM records.
 *
 * See {@https://www.ecmrecords.com/catalogue/143038750696/the-koln-concert-keith-jarrett}
 */
public class Album extends Entity {

    private int releaseYear;

    private String releaseFormat;

    private String genre;

    private String style;

    private String recordNumber;

    private String albumName;

    private Set<Musician> featuredMusicians;

    private Set<MusicianInstrument> instruments;

    private URL albumURL;

    private List<Track> tracks;

    private List<Comment> comments;

    private Set<Group> group;

    public Album(int releaseYear, String recordNumber, String albumName) {
        notNull(recordNumber);
        notNull(albumName);

        notBlank(recordNumber);
        notBlank(albumName);

        this.releaseYear = releaseYear;
        this.recordNumber = recordNumber;
        this.albumName = null;
        this.releaseFormat = null;
        this.genre = null;
        this.style = null;


        this.albumURL = null;

        featuredMusicians = Sets.newHashSet();
        instruments = Sets.newHashSet();
        tracks = Lists.newArrayList();
        comments = Lists.newArrayList();
        group = Sets.newHashSet();
    }

    public String getReleaseFormat() {
        return releaseFormat;
    }

    public void setReleaseFormat(String releaseFormat) {
        notNull(releaseFormat);
        notBlank(releaseFormat);

        this.releaseFormat = releaseFormat;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        notNull(genre);
        notBlank(genre);

        this.genre = genre;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        notNull(style);
        notBlank(style);

        this.style = style;
    }

    public String getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(String recordNumber) {
        notNull(recordNumber);
        notBlank(recordNumber);

        this.recordNumber = recordNumber;
    }

    public Set<Musician> getFeaturedMusicians() {
        return featuredMusicians;
    }

    public void setFeaturedMusicians(Set<Musician> featuredMusicians) {
        notNull(featuredMusicians);

        if (featuredMusicians.size() == 0) {
            throw new IllegalArgumentException("Featured artist list contains no artist");
        }

        this.featuredMusicians = featuredMusicians;
    }

    public Set<Group> getGroup() {
        return group;
    }

    public void setGroup(Set<Group> group) {
        notNull(group);

        if (group.size() == 0) {
            throw new IllegalArgumentException("Group list contains no instrument");
        }

        this.group = group;
    }

    public Set<MusicianInstrument> getInstruments() {
        return instruments;
    }

    public void setInstruments(Set<MusicianInstrument> instruments) {
        notNull(instruments);

        if (instruments.size() == 0) {
            throw new IllegalArgumentException("Instrument list contains no instrument");
        }

        this.instruments = instruments;
    }

    public URL getAlbumURL() {
        return albumURL;
    }

    public void setAlbumURL(URL albumURL) {
        notNull(albumURL);
        this.albumURL = albumURL;
    }

    public List<Comment> getComment() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        notNull(comments);
        if (comments.size() == 0) {
            throw new IllegalArgumentException("Comment list contains no comment");
        }
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        notNull(tracks);
        if (tracks.size() == 0) {
            throw new IllegalArgumentException("Track list contains no track");
        }

        if (tracks.size() != 0) {
            for (Track track : tracks) {
                notNull(track);

                for (Track tra : tracks) {
                    if (track.equals(tra)) {
                        throw new IllegalArgumentException("Two tracks in the same album having the same name is not permitted");
                    }
                }
            }
        }else {
            throw new IllegalArgumentException("Element in tracklist cannot be empty or blank");
        }
        this.tracks = tracks;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        if (releaseYear >= 0 && releaseYear <= Year.now().getValue()) {
            this.releaseYear = releaseYear;
        }else {
            throw new IllegalArgumentException();
        }
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        notNull(albumName);
        notBlank(albumName);

        this.albumName = albumName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return releaseYear == album.releaseYear &&
                recordNumber.equals(album.recordNumber) &&
                albumName.equals(album.albumName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(releaseYear, recordNumber, albumName);
    }
}
