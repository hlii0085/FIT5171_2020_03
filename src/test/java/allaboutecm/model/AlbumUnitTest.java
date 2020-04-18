package allaboutecm.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @DisplayName("Album URL cannot be null")
    public void albumURLCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setAlbumURL(null));
    }

    @Test
    @DisplayName("Tracks cannot be null")
    public void tracksCannotBeNull() {
        assertThrows(NullPointerException.class, () -> album.setTracks(null));
    }

    @Test
    @DisplayName("Two tracks in the same album having the same name is not permitted")
    public void twoTracksCannotHaveSameNameWithinOneAlbum() {
        List<String> tl = new ArrayList<>();
        tl.add("qinghuaci");
        tl.add("niuzaihenmang");
        tl.add("niuzaihenmang");
        assertThrows(IllegalArgumentException.class, () -> album.setTracks(tl));
    }

    @Test
    @DisplayName("Element in tracklist cannot be empty or blank")
    public void elementInTracklistCannotBeEmptyOrBlank() {
        List<String> tl = new ArrayList<>();
        tl.add("qinghuaci");
        tl.add("niuzaihenmang");
        tl.add("  ");
        tl.add("    \t");
        assertThrows(IllegalArgumentException.class, () -> album.setTracks(tl));
    }

    @Test
    @DisplayName("Element in tracklist cannot be null")
    public void elementInTracklistCannotBeNull() {
        List<String> tl = new ArrayList<>();
        tl.add("qinghuaci");
        tl.add("niuzaihenmang");
        tl.add(null);
        assertThrows(IllegalArgumentException.class, () -> album.setTracks(tl));
    }

    @Test
    public void sameNameAndNumberMeansSameAlbum() {
        Album album1 = new Album(1975, "ECM 1064/65", "The Köln Concert");

        assertEquals(album, album1);
    }
}