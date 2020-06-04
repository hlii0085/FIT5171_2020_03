package allaboutecm.model;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import javax.sound.midi.Instrument;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class AlbumUnitTest {
    private Album album;

    @BeforeEach
    public void setUp() {
        album = new Album(1975, "ECM 1064/65", "The Köln Concert");
    }

    @ParameterizedTest
    @ValueSource(ints = {-2000, -1, 2021, 2022})
    @DisplayName("Release year should be a positive number and no more than the current year")
    public void releaseYearPositiveNoMoreThanCurrentYear(int year) {
        assertThrows(IllegalArgumentException.class, () -> album.setReleaseYear(year));
    }

    @Test
    @DisplayName("Record number cannot be null")
    public void recordNumberCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setRecordNumber(null));
    }

    @Test
    @DisplayName("Release Format cannot be null")
    public void releaseFormatCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setReleaseFormat(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("Release Format cannot be empty or blank")
    public void releaseFormatCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> album.setReleaseFormat(arg));
    }

    @Test
    @DisplayName("Genre cannot be null")
    public void genreCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setGenre(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("Genre cannot be empty or blank")
    public void genreCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> album.setGenre(arg));
    }

    @Test
    @DisplayName("Style cannot be null")
    public void styleCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setStyle(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("Style cannot be empty or blank")
    public void styleCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> album.setStyle(arg));
    }

    @Test
    @DisplayName("Album name cannot be null")
    public void albumNameCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setAlbumName(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("Album name cannot be empty or blank")
    public void albumNameCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> album.setAlbumName(arg));
    }

    @Test
    @DisplayName("Featured Artists cannot be null")
    public void featuredArtistsCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setFeaturedMusicians(null));
    }

    @Test
    @DisplayName("There is at least one featured artist")
    public void featuredArtistAtLeastOne() {
        Set<Musician> musicians = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> album.setFeaturedMusicians(musicians));
    }

    @Test
    @DisplayName("Instrument cannot be null")
    public void instrumentCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setInstruments(null));
    }

    @Test
    @DisplayName("There is at least one instrument")
    public void instrumentAtLeastOne() {
        Set<MusicianInstrument> mi = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> album.setInstruments(mi));
    }

    @Test
    @DisplayName("Group cannot be null")
    public void groupCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setGroup(null));
    }

    @Test
    @DisplayName("There is at least one group")
    public void groupAtLeastOne() {
        Set<Group> group = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> album.setGroup(group));
    }

    @Test
    @DisplayName("Album URL cannot be null")
    public void albumURLCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setAlbumURL(null));
    }

    @Test
    @DisplayName("Comments cannot be null")
    public void commentsCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setComments(null));
    }

    @Test
    @DisplayName("There is at least one comment")
    public void commentAtLeastOne() {
        List<Comment> comment = Lists.newArrayList();
        assertThrows(IllegalArgumentException.class, () -> album.setComments(comment));
    }

    @Test
    @DisplayName("Tracks cannot be null")
    public void tracksCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setTracks(null));
    }

    @Test
    @DisplayName("There is at least one track")
    public void trackAtLeastOne() {
        List<Track> tracks = Lists.newArrayList();
        assertThrows(IllegalArgumentException.class, () -> album.setTracks(tracks));
    }

    @Test
    @DisplayName("Two tracks in the same album having the same name is not permitted")
    public void twoTracksCannotHaveSameNameWithinOneAlbum() {
        List<Track> tl = new ArrayList<>();
        Track track1 = new Track("qinghuaci");
        Track track2 = new Track("qinghuaci");
        tl.add(track1);
        tl.add(track2);
        assertThrows(IllegalArgumentException.class, () -> album.setTracks(tl));
    }

    @Test
    public void sameNameAndNumberMeansSameAlbum() {
        Album album1 = new Album(1975, "ECM 1064/65", "The Köln Concert");

        assertEquals(album, album1);
    }

    @ParameterizedTest
    @ValueSource(ints = {-2000, -1})
    @DisplayName("The number of sales should be a positive number")
    public void salesPositive(int sales) {
        assertThrows(IllegalArgumentException.class, () -> album.setSales(sales));
    }

    @Test
    @DisplayName("New Release Year is assigned successfully")
    public void newReleaseYearValueAssignment() {
        int releaseYear = album.getReleaseYear();
        album.setReleaseYear(1980);
        int releaseYear1 = album.getReleaseYear();
        assertNotEquals(releaseYear,releaseYear1);
    }

    @Test
    @DisplayName("New Record Number is assigned successfully")
    public void newRecordNumberValueAssignment() {
        String recordNumber = album.getRecordNumber();
        album.setRecordNumber("ECG 106");
        String recordNumber1 = album.getRecordNumber();
        assertNotEquals(recordNumber,recordNumber1);
    }
    @Test
    @DisplayName("New Album Name  is assigned successfully")
    public void newAlbumNameValueAssignment() {
        String albumName = album.getAlbumName();
        album.setAlbumName("Maps 21");
        String albumName1 = album.getAlbumName();
        assertNotEquals(albumName,albumName1);
    }
    @Test
    @DisplayName(" Musicians are added to the album assigned successfully")
    public void addingMusiciansToAlbum() {
        Set<Musician> musicians = new HashSet<>(); //
        musicians.add(new Musician("Adam Bryan"));
        album.setFeaturedMusicians(musicians);
        assertTrue(album.getFeaturedMusicians().size() ==(1));
    }

    @Test
    @DisplayName(" Instruments are added to the album assigned successfully")
    public void addMusicianInstrument() {
        Set <MusicianInstrument> set =new HashSet(); //List of items with no duplicates
        set.add(new MusicianInstrument(
                new Musician("Adam Bryan"),
                new  HashSet<MusicalInstrument>(){{ add(new MusicalInstrument("Saxophone")); }}
        ));
        album.setInstruments(set);
        assertTrue(album.getInstruments().size() ==(1));
    }

    @Test
    @DisplayName("HashSet should give distinct Instruments")
    public void noDuplicateMusicianInstrument() {
        Set<MusicianInstrument> set =new HashSet();
        set.add(new MusicianInstrument(
                new Musician("Adam Bryan"),
                new  HashSet<MusicalInstrument>(){{ add(new MusicalInstrument("Saxophone")); }}
        ));
        set.add(new MusicianInstrument(
                new Musician("Adam Bryan"),
                new  HashSet<MusicalInstrument>(){{ add(new MusicalInstrument("Saxophone")); }}
        ));
        album.setInstruments(set);
        assertTrue(album.getInstruments().size() ==(1));
    }

    @Test
    @DisplayName(" Tracks are added to the album assigned successfully")
    public void addTracks() {
        List<Track> list =new ArrayList();
        Track track = new Track("Red");
        list.add(track);
        album.setTracks(list);
        assertTrue(album.getTracks().size() ==(1));
    }

    private boolean custom(String s1) {                             // Regular Expression to check whether given string should  have alphabets and Numbers
        return s1.matches("^[a-zA-Z0-9\\s]+$");
    }
    private boolean custom1(String s1) {
        return s1.matches("[0-9]+");
    }

    @Test
    @DisplayName("Record Number should not accept Special characters ")
    public void recordNumberShouldNotAcceptSpecialCharacters() {
        album.setRecordNumber("@1rhdhk" );
        assertEquals(false , custom(album.getRecordNumber()));
    }
    @Test
    @DisplayName("Record Number should  accept  numeric values ")
    public void recordNumberShouldAcceptNumericValues() {
        album.setRecordNumber("21" );
        assertEquals(true , custom(album.getRecordNumber()));
    }
    @Test
    @DisplayName("Record Number should  not accept ONLY alphabets  ")
    public void recordNumberShouldNotAcceptOnlyAlphabets() {
        album.setRecordNumber("abc" );
        assertEquals(false , custom1(album.getRecordNumber()));
    }
    @Test
    @DisplayName("Record Number should  accept Alphanumeric characters ")
    public void recordNumberShouldAcceptAlphanumeric() {
        album.setRecordNumber("12fgj" );
        assertEquals(true , custom(album.getRecordNumber()));
    }

    public void albumNameShouldNotAcceptSpecialCharacters() {
        album.setAlbumName("@gjjj" );
        assertEquals(false , custom(album.getAlbumName()));
    }

    @Test
    @DisplayName("Album Name should  accept Alphanumeric ")
    public void albumNameShouldAcceptAlphanumeric() {
        album.setAlbumName("BT21" );
        assertEquals(true , custom(album.getAlbumName()));
    }

    @Test
    @DisplayName("Album Name can  accept Numeric Values ")
    public void albumNameCanAcceptNumericValues() {
        album.setAlbumName("21" );
        assertEquals(true , custom(album.getAlbumName()));
    }

    @Test
    @DisplayName("Album Name can  accept Alphabets ")
    public void albumNameCanAcceptAlphabets() {
        album.setAlbumName("AndBand" );
        assertEquals(true , custom(album.getAlbumName()));
    }

}
