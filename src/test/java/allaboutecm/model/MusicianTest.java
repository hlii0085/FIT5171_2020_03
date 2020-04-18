package allaboutecm.model;

import com.google.common.collect.Sets;
import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MusicianTest {

    private Musician musician;

    @BeforeEach
    public void setUp() {
        musician = new Musician("Edison Wang");
    }

    /**
     * Equivalent class test for name
     */
    @Test
    @DisplayName("Musician name cannot be null")
    public void testNameCannotBeNull() {
        assertThrows(NullPointerException.class, () -> musician.setName(null));
    }

    /**
     * @param arg
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t", "    \n"})
    @DisplayName("Musician name cannot be empty or blank")
    public void testNameCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> musician.setName(arg));
    }

    @Test
    @DisplayName("Musician name can contain two letters")
    public void testNameContainTwoLetters() {
        musician.setName("Tony Wang");

        assertEquals("Tony Wang", musician.getName(), "Musician Name should be at least two letters");
        assertEquals(musician.getName().split(" ").length, 2);
    }

    @Test
    @DisplayName("Musician name can contain three letters")
    public void testNameContainThreeLetters() {
        musician.setName("Tony Wang Steven");

        assertEquals("Tony Wang Steven", musician.getName(), "Musician Name could contain three letters");
        assertEquals(musician.getName().split(" ").length, 3);
    }

    @Test
    @DisplayName("Musician name cannot contain spaces before or after name")
    public void testNameCannotContainSpaceBeforeOrAfter() {
        assertThrows(IllegalArgumentException.class, () -> musician.setName("   Wang   "));

        try{
            musician.setName("   Wang   ");
        } catch(IllegalArgumentException e){
            assertEquals("name must contain one blank", e.getMessage());
        }
    }

    @Test
    @DisplayName("Musician name cannot contain no space between letters")
    public void testNameCannotContainNoSpace() {
        assertThrows(IllegalArgumentException.class, () -> musician.setName("Wang"));

        try{
            musician.setName("Wang");
        } catch(IllegalArgumentException e){
            assertEquals("name must contain one blank", e.getMessage());
        }
    }

    @Test
    @DisplayName("Musician name cannot contain two spaces between letters")
    public void testNameCannotTwoSpaces() {
        assertThrows(IllegalArgumentException.class, () -> musician.setName("Wang  Tony"));

        try{
            musician.setName("Wang");
        } catch(IllegalArgumentException e){
            assertEquals("name must contain one blank", e.getMessage());
        }
    }

    @Test
    @DisplayName("Same name means same musicians")
    public void testEqualNameMeansSameMusicians() {
        Musician musicianTwo = new Musician("Edison Wang");

        assertEquals(musician, musicianTwo, "Same name means same musicians");
    }

    @Test
    @DisplayName("Different names means different musicians")
    public void testEqualEmailsMeansSameMusicians() {
        Musician musicianTwo = new Musician("Tony Wang");

        assertNotEquals(musician, musicianTwo, "Same name means same musicians");
    }

    /**
     * Equivalent class test for name
     */
    @Test
    @DisplayName("Musician url cannot be null")
    public void testMusicianUrlCannotBeNull() {
        assertThrows(NullPointerException.class, () -> musician.setMusicianUrl(null));
    }

    /**
     * @param arg
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t", "    \n"})
    @DisplayName("Musician url cannot be empty or blank")
    public void testMusicianUrlCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> musician.setName(arg));
    }

    /**
     * @param arg
     */
    @ParameterizedTest
    @ValueSource(strings = {"33??3..-s.co", "dss aasd fd"})
    @DisplayName("Musician url cannot be invalid")
    public void testMusicianUrlInvalidNotAllowed(String arg){
        assertThrows(MalformedURLException.class, () -> musician.setMusicianUrl(arg));
    }

    @Test
    @DisplayName("Musician url valid pass")
    public void testMusicianUrlValidAllowed() {
        String url = "http://www.tonywang.com/index.php";
        try {
            musician.setMusicianUrl(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        assertEquals("http", musician.getMusicianUrl().getProtocol());
        assertEquals("www.tonywang.com", musician.getMusicianUrl().getHost());
        assertEquals("/index.php", musician.getMusicianUrl().getFile());
    }

    /**
     * Equivalent class test for albums
     */
    @Test
    @DisplayName("Albums cannot be null")
    public void albumsCannotBeNull() {
        assertThrows(NullPointerException.class, () -> musician.setAlbums(null));
    }

    @Test
    @DisplayName("Same Album could only appear once")
    public void testSameAlbumEqualsOne(){
        Set<Album> albums = new HashSet<Album>();
        Album albumA = new Album(1999,"123","albumOne");
        Album albumB = new Album(1999,"123","albumOne");
        Album albumC = new Album(1989,"1234","albumTwo");
        albums.add(albumA);
        albums.add(albumB);
        albums.add(albumC);
        assertThrows(IllegalArgumentException.class, () ->musician.setAlbums(albums));
    }

    @Test
    @DisplayName("Wikipedia URL cannot be null")
    public void wikipediaURLCannotBeNull() {
        assertThrows(NullPointerException.class, () -> musician.setWikipediaUrl(null));
    }

    @Test
    @DisplayName("There is at least one fan site")
    public void fanSiteAtLeastOne() {
        Set<URL> fs = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> musician.setFanSites(fs));
    }

    @Test
    @DisplayName("Biography cannot be null")
    public void biographyCannotBeNull() {
        assertThrows(NullPointerException.class, () -> musician.setBiography(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("Biography cannot be empty or blank")
    public void biographyCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> musician.setBiography(arg));
    }
}