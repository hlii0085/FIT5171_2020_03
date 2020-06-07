package allaboutecm.model;

import com.google.common.collect.Sets;
import jdk.nashorn.internal.objects.annotations.Property;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.time.Year;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import static org.apache.commons.lang3.Validate.*;

@NodeEntity
public class Concert {

    @Property(name="year")
    private int year;

    @Property(name="month")
    private String month;

    @Property(name="day")
    private int day;

    @Relationship(type="artists")
    private Set<Musician> artists;

    @Relationship(type="events")
    private Set<String> events;

    @Property(name="day")
    private String city;

    @Property(name="day")
    private String country;

    public Concert() {

        this.year = 0;
        this.month = "";
        this.day = 0;
        artists = Sets.newHashSet();
        this.city = "";
        this.country = "";
        events = Sets.newHashSet();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        notNull(year);

        if (year >= 0 && year <= Year.now().getValue()) {
            this.year = year;
        }else {
            throw new IllegalArgumentException();
        }
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        notNull(month);
        notBlank(month);

        List<String> months =new ArrayList<>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        for (String m : months)
        {
            if (month.equals(m))
            {
                this.month = month;
            }else {
            throw new IllegalArgumentException();
        }
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        notNull(day);

        if (day >= 0 && day <= 31) {
            this.day = day;
        }else {
            throw new IllegalArgumentException();
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        notNull(city);
        notBlank(city);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        notNull(country);
        notBlank(country);
    }

    public Set<Musician> getArtists() {
        return artists;
    }

    public void setArtists(Set<Musician> artists) {
        notNull(artists);

        if (artists.isEmpty()) {
            throw new IllegalArgumentException("Artists list contains no instrument");
        }

        this.artists = artists;
    }

    public Set<String> getEvents() {
        return events;
    }

    public void setEvents(Set<String> events) {
        notNull(events);

        this.events = events;
    }

}
