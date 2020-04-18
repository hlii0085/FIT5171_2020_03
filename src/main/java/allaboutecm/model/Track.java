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


public class Track  extends Entity {

    private String trackName;

    private String length;

    private Musician musician;

    public Track(String trackName) {
        notNull(trackName);
        notBlank(trackName);
        this.trackName = trackName;
        this.length = null;
        musician = new Musician(null);
    }

    public Musician getMusician() {
        return musician;
    }

    public void setMusician(Musician musician) {
        this.musician = musician;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        notNull(trackName);
        notBlank(trackName);

        this.trackName = trackName;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        notNull(length);
        notBlank(length);
        String str = length.trim();
        int i = str.length();
        if (i == 5 && Character.isDigit(str.charAt(0)) && Character.isDigit(str.charAt(1)) &&
                Character.isDigit(str.charAt(3)) && Character.isDigit(str.charAt(4)) && str.charAt(2) == ':') {
            this.length = length;
        } else {
            throw new IllegalArgumentException("Length of track is invalid"); }
        }
}