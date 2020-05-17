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
        Configuration configuration = new Configuration.Builder().build();

        // Disk-based embedded store
        // Configuration configuration = new Configuration.Builder().uri(new File(TEST_DB).toURI().toString()).build();

        // HTTP data store, need to install the Neo4j desktop app and create & run a database first.
//        Configuration configuration = new Configuration.Builder().uri("http://neo4j:password@localhost:7474").build();

        sessionFactory = new SessionFactory(configuration, Musician.class.getPackage().getName());
        session = sessionFactory.openSession();

        dao = new Neo4jDAO(session);
    }

    @AfterEach
    public void tearDownEach() {
        session.purgeDatabase();
    }

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
    
    //查
    @DisplayName("test whether can find musician by name")
    @Test
    public void testFindMusicianByName(){
        Musician musician = new Musician("Keith Jarret");
        dao.createOrUpdate(musician);
        Musician loadedMusician = dao.findMusicianByName(musician.getName());
        assertEquals(musician, loadedMusician, "the corresponding musician should be found");
    }

    //删 没有加内容在原来代码
    @DisplayName("test delete musician will not delete album")
    @Test
    public void testDeleteMusicianNotDeleteAlbum() throws MalformedURLException{
        Musician musician = new Musician("Keith Jarret");
        musician.setMusicianUrl("https://www.keithjarrett.org/");
        Album album = new Album(1975,"ECM 1862/65","The Köln Concert");

        musician.setAlbums(Sets.newHashSet(album));

        dao.createOrUpdate(album);
        dao.createOrUpdate(musician);

        assertNotNull(dao.load(Musician.class, musician.getId()).getId(), "Musician saved");
        assertNotNull(dao.loadAll(Album.class),"Album saved");

        dao.delete(musician);

        assertTrue(dao.loadAll(Musician.class).isEmpty(), "musician should no longer exists");
        assertFalse(dao.loadAll(Album.class).isEmpty(), "album should no longer exists" );
    }

    //加了delete
    @DisplayName("test delete musician also delete the musician instrument")
    @Test
    public void testDeleteMusicianAlsoDeleteMusicianInstrument(){
        Musician musician = new Musician("Keith Jarret");
        dao.createOrUpdate(musician);
        MusicianInstrument musicianInstrument = new MusicianInstrument(musician,
                Sets.newHashSet(new MusicalInstrument("Violin")));

        dao.createOrUpdate(musicianInstrument);
        dao.delete(musician);
        assertNull(dao.load(Musician.class,musician.getId()),"Musician should be delete");
        assertNull(dao.load(MusicianInstrument.class,musicianInstrument.getId()),"Musician instrument should be delete");
    }

}