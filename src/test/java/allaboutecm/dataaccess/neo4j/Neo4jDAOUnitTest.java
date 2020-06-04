package allaboutecm.dataaccess.neo4j;

import allaboutecm.dataaccess.DAO;
import allaboutecm.model.Album;
import allaboutecm.model.MusicalInstrument;
import allaboutecm.model.Musician;
import allaboutecm.model.MusicianInstrument;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.*;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.neo4j.ogm.support.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * TODO: add test cases to adequately test the Neo4jDAO class.
 */
class Neo4jDAOUnitTest {
    private static final String TEST_DB = "target/test-data/test-db.neo4j";

    private static DAO dao;
    private static Session session;
    private static SessionFactory sessionFactory;

    @BeforeAll
    public static void setUp() {
        // See @https://neo4j.com/docs/ogm-manual/current/reference/ for more information.

        // To use an impermanent embedded data store which will be deleted on shutdown of the JVM,
        // you just omit the URI attribute.

        // Impermanent embedded store
        // virtual
        Configuration configuration = new Configuration.Builder().build();

        // Disk-based embedded store
        // Configuration configuration = new Configuration.Builder().uri(new File(TEST_DB).toURI().toString()).build();

        // HTTP data store, need to install the Neo4j desktop app and create & run a database first.
        //session.purgeDatabase();
        //Configuration configuration = new Configuration.Builder().uri("http://neo4j:951228@localhost:7474").build();

        sessionFactory = new SessionFactory(configuration, Musician.class.getPackage().getName());
        session = sessionFactory.openSession();

        dao = new Neo4jDAO(session);
    }

    @AfterEach
    public void tearDownEach() {

        session.purgeDatabase();
    }
    //empty database

    @AfterAll
    public static void tearDown() throws IOException {
        session.purgeDatabase();
        session.clear();
        sessionFactory.close();
        File testDir = new File(TEST_DB);
        if (testDir.exists()) {
//            FileUtils.deleteDirectory(testDir.toPath());
        }
    }

    @Test
    public void daoIsNotEmpty() {
        assertNotNull(dao);
    }

    @Test
    public void successfulCreationAndLoadingOfMusician() throws MalformedURLException {
        // test if the initial musician table is empty
        assertEquals(0, dao.loadAll(Musician.class).size());

        Musician musician = new Musician("Keith Jarrett");
        musician.setMusicianUrl("https://www.keithjarrett.org/");

        dao.createOrUpdate(musician);
        Musician loadedMusician = dao.load(Musician.class, musician.getId());

        assertNotNull(loadedMusician.getId());
        assertEquals(musician, loadedMusician);
        assertEquals(musician.getMusicianUrl(), loadedMusician.getMusicianUrl());

        assertEquals(1, dao.loadAll(Musician.class).size());

//        dao.delete(musician);
//        assertEquals(0, dao.loadAll(Musician.class).size());
    }

    @Test
    public void successfulCreationOfMusicianAndAlbum() throws MalformedURLException {
        Musician musician = new Musician("Keith Jarrett");
        musician.setMusicianUrl("https://www.keithjarrett.org/");

        Album album = new Album(1975, "ECM 1064/65", "The Köln Concert");
        musician.setAlbums(Sets.newHashSet(album));

        dao.createOrUpdate(album);
        dao.createOrUpdate(musician);

        Collection<Musician> musicians = dao.loadAll(Musician.class);
        assertEquals(1, musicians.size());
        Musician loadedMusician = musicians.iterator().next();
        assertEquals(musician, loadedMusician);
        assertEquals(musician.getMusicianUrl(), loadedMusician.getMusicianUrl());
        assertEquals(musician.getAlbums(), loadedMusician.getAlbums());
    }

    @Test
    public void sameMusicianCannotBeSavedTwice() throws MalformedURLException{
        Musician musician1 = new Musician("Taylor Swift");
        musician1.setMusicianUrl("https://www.taylorswift.com/");

        dao.createOrUpdate(musician1);

        Musician musician2 = new Musician("Taylor Swift");
        musician2.setMusicianUrl("https://www.taylorswift.com/");

        dao.createOrUpdate(musician2);

        Collection<Musician> musicians = dao.loadAll(Musician.class);
        assertEquals(1, musicians.size());
        assertEquals(musician1.getName(), musicians.iterator().next().getName());
    }

    @Test
    public void createThreeMusiciansSimultaneously(){
        Set<Musician> musicians = Sets.newHashSet(
                new Musician("Claire Cottrill"),
                new Musician("Ellie Rowsell"),
                new Musician("Mckenna Petty")
        );

        for (Musician m : musicians){
            dao.createOrUpdate(m);
        }

        Collection<Musician> loadedMusicians = dao.loadAll(Musician.class);
        assertEquals(musicians.size(), loadedMusicians.size(), "same size");
        for (Musician m : musicians){
            assertTrue(musicians.contains(m), "contains " + m.getName());
        }
    }

    @Test
    public void musicianInformationCanBeUpdated() throws MalformedURLException{
        Musician musician = new Musician("Paul Klein");
        musician.setMusicianUrl("https://www.lany.com/");

        dao.createOrUpdate(musician);
        musician.setName("Jake Goss");

        Musician loadedMusician = dao.load(Musician.class, musician.getId());
        assertEquals(musician.getName(), loadedMusician.getName());
    }
    
    //search
    @DisplayName("Test whether can find musician by name")
    @Test
    public void testFindMusicianByName(){
        Musician musician = new Musician("Keith Jarret");
        dao.createOrUpdate(musician);
        Musician loadedMusician = dao.findMusicianByName(musician.getName());
        assertEquals(musician, loadedMusician, "the corresponding musician should be found");
    }

    //
    @DisplayName("Test delete musician will not delete album")
    @Test
    public void testDeleteMusicianNotDeleteAlbum() throws MalformedURLException{
        Musician musician = new Musician("Keith Jarret");
        musician.setMusicianUrl("https://www.keithjarrett.org/");
        Album album = new Album(1975,"ECM 1862/65","The Köln Concert");

        musician.setAlbums(Sets.newHashSet(album));

        dao.createOrUpdate(album);
        dao.createOrUpdate(musician);

        dao.delete(musician);

        assertTrue(dao.loadAll(Musician.class).isEmpty(), "musician delete");
        assertFalse(dao.loadAll(Album.class).isEmpty(), "album exists" );
    }

    //add delete
    @DisplayName("Test delete musician also delete the musician instrument")
    @Test
    public void testDeleteMusicianAlsoDeleteMusicianInstrument(){
        Musician musician = new Musician("Keith Jarret");
        dao.createOrUpdate(musician);
        MusicianInstrument musicianInstrument = new MusicianInstrument(musician,
                Sets.newHashSet(new MusicalInstrument("Violin")));

        dao.createOrUpdate(musicianInstrument);
        dao.delete(musician);
        assertNull(dao.load(Musician.class,musician.getId()),"Musician delete");
        assertNull(dao.load(MusicianInstrument.class,musicianInstrument.getId()),"Musician instrument also delete");
    }

    @Test
    public void successfulCreationAndLoadingOfAlbum() {

        assertEquals(0, dao.loadAll(Album.class).size());


        Album album = new Album(2020,"112","MOTS8");

        dao.createOrUpdate(album);
        Album loadedAlbum = dao.load(Album.class, album.getId());

        assertNotNull(loadedAlbum.getId());
        assertEquals(album, loadedAlbum);
        assertEquals(1, dao.loadAll(Album.class).size());

    }

    @Test
    public void successfullyGetAlbumByYear() {
        assertEquals(0, dao.loadAll(Album.class).size());
        Album album = new Album(2020,"112","MOTS8");

        dao.createOrUpdate(album);
        Collection<Album> albumByYear = dao.findAlbumsByYear(2020);

        assertEquals(1,albumByYear.size());
    }

    @Test
    public void whenMatchedAlbumDoesntExistShouldReturnEmptyList() {
        assertEquals(0, dao.loadAll(Album.class).size());
        Album album = new Album(2020,"112","MOTS8");

        dao.createOrUpdate(album);
        Collection<Album> albumByYear = dao.findAlbumsByYear(2021);

        assertEquals(0,albumByYear.size());
    }

    @Test
    public void whenYearIsNegativeOrZeroShouldThrowException() {
        //If the given year is zero or lesser than Zero. from the code we are throwing illegal argument exception as it is invalid year
        assertThrows(IllegalArgumentException.class, () -> dao.findAlbumsByYear(-2020) );
    }

    @Test
    public void successfulCreationOfAlbumInDBAndFindAlbumByName() {
        assertEquals(0, dao.loadAll(Album.class).size());
        Album album = new Album(2020,"112","MOTS8");
        dao.createOrUpdate(album);
        Album foundAlbum = dao.findAlbumByName("MOTS8");
        assertEquals(album, foundAlbum);
    }

    @Test
    public void successfulCreationOfAlbumInDBAndFindAlbumByNameIrrespectiveOfCase() {
        assertEquals(0, dao.loadAll(Album.class).size());
        Album album = new Album(2020,"112","RASH");
        dao.createOrUpdate(album);
        Album foundAlbum = dao.findAlbumByName("rash");
        assertEquals(album, foundAlbum);
    }

    @Test
    public void successfulCreationOfAlbumInDBAndFindAlbumByNameForEmptyValues() {
        assertEquals(0, dao.loadAll(Album.class).size());
        Album album = new Album(2020,"112","MOTS8");
        dao.createOrUpdate(album);
        Album foundAlbum = dao.findAlbumByName("");
        assertNull(foundAlbum);
    }

    @Test
    public void successfulCreationOfAlbumInDBAndFindAlbumByNameForNullValues() {
        assertEquals(0, dao.loadAll(Album.class).size());
        Album album = new Album(2020, "112", "MOTS8");
        dao.createOrUpdate(album);
        Album foundAlbum = dao.findAlbumByName(null);
        assertNull(foundAlbum);
    }

    // To add or update album url
    @Test
    public void  createOrUpdateAlbumUrl() throws MalformedURLException{
        Album album = new Album(2020,"112","MOTS8");
        album.setAlbumURL(new URL("https://www.keithjarrett.org/"));
        dao.createOrUpdate(album);
        Album getAlbum = dao.findAlbumByURL(new URL("https://www.keithjarrett.org/"));
        assertEquals(album,getAlbum);
    }

    @Test
    public void  createOrUpdateMusicianByName() throws MalformedURLException{
        Musician musician = new Musician("becky li");
        musician.setMusicianUrl("https://www.boxigo.in/");
        dao.createOrUpdate(musician);
        Musician getMusician = dao.findMusicianByName("becky li");
        assertEquals(musician,getMusician);
    }

    @Test
    public void  createOrUpdateMusicianByAlbum() throws MalformedURLException{
        Musician musician = new Musician("becky li");
        musician.setMusicianUrl("https://www.becky.org/");

        Album album = new Album(1992, "t 101", "The becky");
        musician.setAlbums(Sets.newHashSet(album));

        dao.createOrUpdate(album);
        dao.createOrUpdate(musician);

        Album albumByRecordNumber = dao.findAlbumByRecordNumber("The becky");
        Collection<Musician> musicians1 = dao.loadAll(Musician.class);
        assertEquals(1, musicians1.size());
        Musician loadedMusician = musicians1.iterator().next();
        assertEquals(musician.getAlbums(), loadedMusician.getAlbums());
        if (musician.getAlbums() == loadedMusician.getAlbums()) {
            assertEquals(musician.getName(), loadedMusician.getName());
        }
    }

    @Test
    public void createOrUpdateMusicianByRecordNumber() throws MalformedURLException {
        Musician musician = new Musician("becky li");
        musician.setMusicianUrl("https://www.becky.org/");

        Album album = new Album(1992, "t 101", "The becky");
        musician.setAlbums(Sets.newHashSet(album));

        dao.createOrUpdate(album);
        dao.createOrUpdate(musician);

        Album albumByRecordNumber = dao.findAlbumByRecordNumber("t 101");
        System.out.println(albumByRecordNumber.getRecordNumber());
        assertEquals(album,albumByRecordNumber);
        System.out.println(musician.getAlbums());
        Collection<Album> albumtest = musician.getAlbums();
        for (Album alb : albumtest)
            assertEquals(alb.getRecordNumber(),albumByRecordNumber.getRecordNumber());
    }
}