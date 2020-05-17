package allaboutecm.mining;

import allaboutecm.dataaccess.DAO;
import allaboutecm.dataaccess.neo4j.Neo4jDAO;
import allaboutecm.model.Album;
import allaboutecm.model.MusicalInstrument;
import allaboutecm.model.Musician;
import allaboutecm.model.MusicianInstrument;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Year;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TODO: perform unit testing on the ECMMiner class, by making use of mocking.
 */
class ECMMinerUnitTest {
    private DAO dao;
    private ECMMiner ecmMiner;

    @BeforeEach
    public void setUp() {

        dao = mock(Neo4jDAO.class);
        ecmMiner = new ECMMiner(dao);
    }

    // Test mostProlificMusicians
    @Test
    public void shouldReturnTheMusicianWhenThereIsOnlyOne() {
        Album album = new Album(1975, "ECM 1064/65", "The Köln Concert");
        Musician musician = new Musician("Keith Jarrett");
        musician.setAlbums(Sets.newHashSet(album));
        when(dao.loadAll(Musician.class)).thenReturn(Sets.newHashSet(musician));

        List<Musician> musicians = ecmMiner.mostProlificMusicians(5, 1970, 1980);

        assertEquals(1, musicians.size());
        assertTrue(musicians.contains(musician));
    }


    @DisplayName("when K invalid return empty list")
    @ParameterizedTest
    @ValueSource(ints = {-5, -1, 0})
    public void testMostProlificMusiciansReturnEmptyListWhenKInvalid(int arg){
        Album album = new Album(1975, "ECM 1064/65", "The Köln Concert");
        Musician musician = new Musician("Keith Jarrett");
        musician.setAlbums(Sets.newHashSet(album));

        when(dao.loadAll(Musician.class)).thenReturn(Sets.newHashSet(musician));

        List<Musician> musicians = ecmMiner.mostProlificMusicians(arg,1975,2000);
        assertEquals(0,musicians.size());
    }

    @DisplayName("when start year and end year invalid")
    @Test
    public void testMostProlificMusiciansReturnEmptyListWhenStartYearOrEndYearInvalid(){
        Album album = new Album(1975, "ECM 1064/65", "The Köln Concert");
        Musician musician = new Musician("Keith Jarrett");
        musician.setAlbums(Sets.newHashSet(album));

        when(dao.loadAll(Musician.class)).thenReturn(Sets.newHashSet(musician));

        List<Musician> musicians = ecmMiner.mostProlificMusicians(1,-1,-1);
        assertEquals(0,musicians.size(),"the year should bigger than 0");

        musicians = ecmMiner.mostProlificMusicians(1,1970,1969);
        assertEquals(0,musicians.size(),"the end year cannot before start year");

        musicians = ecmMiner.mostProlificMusicians(1,2030,2100);
        assertEquals(0,musicians.size(),"The year should before this year");
    }

    @DisplayName("when K larger than musicians")
    @ParameterizedTest
    @ValueSource(ints = {2, 100, 1000})
    public void testMostProlificMusiciansKLargerThanMusicians(int arg){
        Album album = new Album(1975, "ECM 1064/65", "The Köln Concert");
        Musician musician = new Musician("Keith Jarrett");
        musician.setAlbums(Sets.newHashSet(album));

        when(dao.loadAll(Musician.class)).thenReturn(Sets.newHashSet(musician));

        List<Musician> musicians = ecmMiner.mostProlificMusicians(arg,1970,2000 );
        assertEquals(1,musicians.size());
        assertTrue(musicians.contains(musician));
    }

    //when K is greater than musicians return musicians we have
    @Test
    public void testMostProlificMusiciansKLargerAndEqualsMusiciansReturnMusiciansAmount(){
        HashSet<Album> musicianAAlbums = Sets.newHashSet(new Album(1975, "ECM 1064/65", "The Köln Concert"),
                new Album(1985, "ECM 1065/66", "The AAA Concert"));
        Musician musicianA = new Musician("Keith Jarrett");
        musicianA.setAlbums(musicianAAlbums);

        HashSet<Album> musicianBAlbums = Sets.newHashSet(new Album(1995, "ECM 1066/67", "The BBB Concert"),
                new Album(1996, "ECM 1067/68", "The CCC Concert"));
        Musician musicianB = new Musician("Kainan Liang");
        musicianB.setAlbums(musicianBAlbums);

        when(dao.loadAll(Musician.class)).thenReturn(Sets.newHashSet(musicianA,musicianB));

        List<Musician> musicians = ecmMiner.mostProlificMusicians(1,1975,2000 );
        assertEquals(1, musicians.size());
        assertTrue(musicians.contains(musicianA) || musicians.contains(musicianB));
    }

    // Test MostTalentMusicians
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, 0})
    public void testMostTalentMusicians(int arg){
        Musician musicianA = new Musician("Keith Jarrett");
        MusicianInstrument musicianInstrument = new MusicianInstrument(musicianA, Sets.newHashSet(new MusicalInstrument("Piano")));

        when(dao.loadAll(MusicianInstrument.class)).thenReturn(Sets.newHashSet(musicianInstrument));
        List<Musician> musicians = ecmMiner.mostTalentedMusicians(arg);
        assertEquals(0,musicians.size(),"the list should be empty if k is invalid");
    }


    @Test
    public void testReturnKLargerMusicians(){
        HashSet<MusicianInstrument> musicianInstruments = Sets.newHashSet(
                new MusicianInstrument(
                        new Musician("Kainan Liang"), Sets.newHashSet(
                        new MusicalInstrument("Piano"))));
        when(dao.loadAll(MusicianInstrument.class)).thenReturn(musicianInstruments);

        List<Musician> musicians = ecmMiner.mostTalentedMusicians(2);
        assertEquals(1, musicians.size());
        assertTrue(musicians.contains(new Musician("Kainan Liang")));
    }

    @Test
    public void testMostTalentMusicianReturnKLargerThanEqualNumber(){
        HashSet<MusicianInstrument> musicianInstruments = Sets.newHashSet(
                new MusicianInstrument(
                        new Musician("Kainan Liang"), Sets.newHashSet(
                        new MusicalInstrument("Piano"),new MusicalInstrument("Violet"),new MusicalInstrument("trumpet"))),
                new MusicianInstrument(
                        new Musician("Tom Wang"), Sets.newHashSet(
                        new MusicalInstrument("Piano"),new MusicalInstrument("Violet"))),
                new MusicianInstrument(
                        new Musician("Tommy Ji"), Sets.newHashSet(
                        new MusicalInstrument("Piano"),new MusicalInstrument("Violet"))));
        when(dao.loadAll(MusicianInstrument.class)).thenReturn(musicianInstruments);
        List<Musician> musicians = ecmMiner.mostTalentedMusicians(2);
        assertEquals(2, musicians.size());
        assertTrue(musicians.get(0).equals(new Musician("Kainan Liang")));
        assertTrue(musicians.get(1).equals(new Musician("Tom Wang") )||
                musicians.get(1).equals(new Musician("Tommy Ji")));
    }

    // Test mostSocialMusicians

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, 0})
    public void testMostSocialMusicians(int arg){
        Musician musicianA = new Musician("Keith Jarrett");
        MusicianInstrument musicianInstrument = new MusicianInstrument(musicianA, Sets.newHashSet(new MusicalInstrument("Piano")));

        when(dao.loadAll(MusicianInstrument.class)).thenReturn(Sets.newHashSet(musicianInstrument));
        List<Musician> musicians = ecmMiner.mostSocialMusicians(arg);
        assertEquals(0,musicians.size(),"the list should be empty if k is invalid");
    }

    @DisplayName("When K larger than musicians")
    @ParameterizedTest
    @ValueSource(ints = {2, 100, 1000})
    public void testMostSocialMusiciansKLargerThanMusicians(int arg){
        Album album = new Album(1975, "ECM 1064/65", "The Köln Concert");
        Musician musician = new Musician("Keith Jarrett");
        musician.setAlbums(Sets.newHashSet(album));

        when(dao.loadAll(Musician.class)).thenReturn(Sets.newHashSet(musician));

        List<Musician> musicians = ecmMiner.mostProlificMusicians(arg,1970,2000 );
        assertEquals(1,musicians.size());
        assertTrue(musicians.contains(musician));
    }

    @Test
    public void mostSocialMusicianWithCommonK(){
        Musician musicianA = new Musician("A A");
        Musician musicianB = new Musician("B B");
        Musician musicianC = new Musician("C C");
        Musician musicianD = new Musician("D D");

        Album albumA = new Album(1975, "ECM 1064/65", "The Köln Concert");
        Album albumB = new Album(1985, "ECM 1065/65", "The K Concert");

        albumA.setFeaturedMusicians(Sets.newHashSet(musicianA, musicianB));
        albumB.setFeaturedMusicians(Sets.newHashSet(musicianB, musicianC, musicianD));

        musicianA.setAlbums(Sets.newHashSet(albumA));
        musicianB.setAlbums(Sets.newHashSet(albumA, albumB));
        musicianC.setAlbums(Sets.newHashSet(albumB));
        musicianD.setAlbums(Sets.newHashSet(albumB));

        when(dao.loadAll(Musician.class)).thenReturn(Sets.newHashSet(musicianA, musicianB, musicianC, musicianD));
        List<Musician> musicians = ecmMiner.mostSocialMusicians(1);
        assertEquals(1, musicians.size());
        assertTrue(musicians.get(0).equals(musicianB));

        musicians = ecmMiner.mostSocialMusicians(2);
        assertEquals(2, musicians.size());
        assertTrue(musicians.get(1).equals(musicianC) || musicians.get(1).equals(musicianD));

        musicians = ecmMiner.mostSocialMusicians(4);
        assertEquals(4, musicians.size());
        assertTrue(musicians.get(3).equals(musicianA));
    }

    // Test busiest year
    @DisplayName("When Busiest year is invalid")
    @Test
    public void test() {
        Album album = new Album(1975, "ECM 1064/65", "The Köln Concert");
        Musician musician = new Musician("Keith Jarrett");
        musician.setAlbums(Sets.newHashSet(album));

        when(dao.loadAll(Musician.class)).thenReturn(Sets.newHashSet(musician));

        List<Integer> ys = ecmMiner.busiestYears(0);
        assertEquals(0, ys.size(), "The album can not be null");

        ys = ecmMiner.busiestYears(-1);
        assertEquals(0, ys.size(), "The album can not be nul");
    }

    // Test mostSimilarAlbums
    @DisplayName("When K is invalid")
    @Test
    public void testMostSimilarAlbumsReturnEmptyListWhenKInvalid(){
        Album album = new Album(1975, "ECM 1064/65", "The Köln Concert");
        Musician musician = new Musician("Keith Jarrett");
        musician.setAlbums(Sets.newHashSet(album));

        when(dao.loadAll(Musician.class)).thenReturn(Sets.newHashSet(musician));

        List<Album> albums = ecmMiner.mostSimilarAlbums(-1,album);
        assertEquals(0,albums.size(),"the k should bigger than 0");

        albums = ecmMiner.mostSimilarAlbums(0,album);
        assertEquals(0,albums.size(),"the k should bigger than 0");
    }

    @DisplayName("When Album is Null")
    @Test
    public void testMostSimilarAlbumsReturnEmptyListWhenAlbumIsNull(){
        Album album = new Album(1975, "ECM 1064/65", "The Köln Concert");
        Musician musician = new Musician("Keith Jarrett");
        musician.setAlbums(Sets.newHashSet(album));

        when(dao.loadAll(Musician.class)).thenReturn(Sets.newHashSet(musician));

        List<Album> albums = ecmMiner.mostSimilarAlbums(3,null);
        assertEquals(0,albums.size(),"The album can not be null");

        albums = ecmMiner.mostSimilarAlbums(4,null);
        assertEquals(0,albums.size(),"The album can not be nul");
    }
}