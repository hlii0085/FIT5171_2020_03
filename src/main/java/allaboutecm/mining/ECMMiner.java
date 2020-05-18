package allaboutecm.mining;

import allaboutecm.dataaccess.DAO;
import allaboutecm.model.*;
import com.google.common.collect.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;
import java.util.*;

/**
 * TODO: implement and test the methods in this class.
 * Note that you can extend the Neo4jDAO class to make implementing this class easier.
 */
public class ECMMiner {
    private static Logger logger = LoggerFactory.getLogger(ECMMiner.class);

    private final DAO dao;

    public ECMMiner(DAO dao) {
        this.dao = dao;
    }

    /**
     * Returns the most prolific musician in terms of number of albums released.
     *
     * @Param k the number of musicians to be returned.
     * @Param startYear, endYear between the two years [startYear, endYear].
     * When startYear/endYear is negative, that means startYear/endYear is ignored.
     */
    public List<Musician> mostProlificMusicians(int k, int startYear, int endYear) {
        if (k <= 0 || startYear < 0 || endYear < 0 || startYear > Year.now().getValue() || endYear > Year.now().getValue()) {
            return Lists.newArrayList();
        }
        Collection<Musician> musicians = dao.loadAll(Musician.class);
        Map<String, Musician> nameMap = Maps.newHashMap();
        for (Musician m : musicians) {
            nameMap.put(m.getName(), m);
        }

        ListMultimap<String, Album> multimap = MultimapBuilder.treeKeys().arrayListValues().build();
        ListMultimap<Integer, Musician> countMap = MultimapBuilder.treeKeys().arrayListValues().build();

        for (Musician musician : musicians) {
            Set<Album> albums = musician.getAlbums();
            for (Album album : albums) {
                boolean toInclude =
                        !((startYear > 0 && album.getReleaseYear() < startYear) ||
                                (endYear > 0 && album.getReleaseYear() > endYear));

                if (toInclude) {
                    multimap.put(musician.getName(), album);
                }
            }
        }

        Map<String, Collection<Album>> albumMultimap = multimap.asMap();
        for (String name : albumMultimap.keySet()) {
            Collection<Album> albums = albumMultimap.get(name);
            int size = albums.size();
            countMap.put(size, nameMap.get(name));
        }

        List<Musician> result = Lists.newArrayList();
        List<Integer> sortedKeys = Lists.newArrayList(countMap.keySet());
        sortedKeys.sort(Ordering.natural().reverse());
        for (Integer count : sortedKeys) {
            List<Musician> list = countMap.get(count);

            if (result.size() + list.size() >= k) {
                int newAddition = k - result.size();
                for (int i = 0; i < newAddition; i++) {
                    result.add(list.get(i));
                }
            } else {
                result.addAll(list);
            }
        }

        return result;
    }

    /**
     * Most talented musicians by the number of different musical instruments they play
     *
     * @Param k the number of musicians to be returned.
     */
    public List<Musician> mostTalentedMusicians(int k) {
        if (k <= 0) {
            return Lists.newArrayList();
        } else {
            Collection<MusicianInstrument> musicianInstruments = dao.loadAll(MusicianInstrument.class);
            ListMultimap<Integer, Musician> musicianIntegerHashMap = MultimapBuilder.treeKeys().arrayListValues().build();
            for (MusicianInstrument musicianInstrument : musicianInstruments) {
                Musician musician = musicianInstrument.getMusician();
                int size = musicianInstrument.getMusicalInstrument().size();
                musicianIntegerHashMap.put(size, musician);
            }

            List<Musician> result = Lists.newArrayList();
            List<Integer> sortedKeys = Lists.newArrayList(musicianIntegerHashMap.keySet());
            sortedKeys.sort(Ordering.natural().reverse());

            for (Integer count : sortedKeys) {
                List<Musician> list = musicianIntegerHashMap.get(count);
                if (result.size() + list.size() >= k) {
                    int newAddition = k - result.size();
                    for (int i = 0; i < newAddition; i++) {
                        result.add(list.get(i));
                    }
                } else {
                    result.addAll(list);
                }
            }
            return result;
        }
    }

    /**
     * Musicians that collaborate the most widely, by the number of other musicians they work with on albums.
     *
     * @Param k the number of musicians to be returned.
     */

    public List<Musician> mostSocialMusicians(int k) {

        if (k <= 0) {
            return Lists.newArrayList();
        } else {
            Collection<Musician> musicians = dao.loadAll(Musician.class);
            ListMultimap<Integer, Musician> musicianCoopResult = MultimapBuilder.treeKeys().arrayListValues().build();
            for (Musician musician : musicians) {
                Set<Album> albums = musician.getAlbums();
                HashSet<Musician> coopMusician = new HashSet<>();
                for (Album album : albums) {
                    Set<Musician> musicianInAlbum = album.getFeaturedMusicians();
                    coopMusician.addAll(Sets.newHashSet(musicianInAlbum));
                }

                musicianCoopResult.put(coopMusician.size() - 1, musician);
            }
            List<Musician> result = Lists.newArrayList();
            List<Integer> sortedKeys = Lists.newArrayList(musicianCoopResult.keySet());
            sortedKeys.sort(Ordering.natural().reverse());
            for (Integer count : sortedKeys) {
                List<Musician> list = musicianCoopResult.get(count);

                if (result.size() + list.size() >= k) {
                    int newAddition = k - result.size();
                    for (int i = 0; i < newAddition; i++) {
                        result.add(list.get(i));
                    }
                } else {
                    result.addAll(list);
                }
            }
            return result;
        }
    }

    /**
     * Busiest year in terms of number of albums released.
     *
     * @Param k the number of years to be returned.
     */

    public List<Integer> busiestYears(int k) {
        if (k <= 0) {
            return Lists.newArrayList();
        } else {
            Collection<Album> albums = dao.loadAll(Album.class);
            ListMultimap<Integer, Album> yearHashMultiMap = MultimapBuilder.treeKeys().arrayListValues().build();
            for (Album album : albums) {
                yearHashMultiMap.put(album.getReleaseYear(), album);
            }
            ListMultimap<Integer, Integer> yearNumberHashMultiMap = MultimapBuilder.treeKeys().arrayListValues().build();
            for (Integer year : yearHashMultiMap.keySet()) {
                yearNumberHashMultiMap.put(yearHashMultiMap.get(year).size(), year);
            }
            List<Integer> result = Lists.newArrayList();
            List<Integer> sortedKeys = Lists.newArrayList(yearNumberHashMultiMap.keySet());
            sortedKeys.sort(Ordering.natural().reverse());
            for (Integer count : sortedKeys) {
                List<Integer> list = yearNumberHashMultiMap.get(count);
                if (result.size() + list.size() >= k) {
                    int newAddition = k - result.size();
                    for (int i = 0; i < newAddition; i++) {
                        result.add(list.get(i));
                    }
                } else {
                    result.addAll(list);
                }
            }

            return result;
        }
    }

    /**
     * Most similar albums to a give album. The similarity can be defined in a variety of ways.
     * For example, it can be defined over the musicians in albums, the similarity between names
     * of the albums & tracks, etc.
     *
     * @Param k the number of albums to be returned.
     * @Param album
     */

    public List<Album> mostSimilarAlbums(int k, Album album) {
        if (k <= 0 || album == null) {
            return Lists.newArrayList();
        } else {
            Collection<Album> albums = dao.loadAll(Album.class);

            ListMultimap<Integer, Album> albumListMultimap = MultimapBuilder.treeKeys().arrayListValues().build();

            for (Album tempAlbum : albums) {
                int countRelatedNumber = 0;
                if (album.getAlbumName().equals(tempAlbum.getAlbumName())) {
                    countRelatedNumber++;
                }
                if (album.getReleaseYear() == tempAlbum.getReleaseYear()) {
                    countRelatedNumber++;
                }
                for (Track track : tempAlbum.getTracks()) {
                    if (album.getTracks().contains(track)) {
                        countRelatedNumber++;
                    }
                }

                albumListMultimap.put(countRelatedNumber, tempAlbum);
            }
            List<Album> result = Lists.newArrayList();
            List<Integer> sortedKeys = Lists.newArrayList(albumListMultimap.keySet());
            sortedKeys.sort(Ordering.natural().reverse());
            for (Integer count : sortedKeys) {
                List<Album> list = albumListMultimap.get(count);

                if (result.size() + list.size() >= k) {
                    int newAddition = k - result.size();
                    for (int i = 0; i < newAddition; i++) {
                        result.add(list.get(i));
                    }
                } else {
                    result.addAll(list);
                }
            }

            return result;
        }
    }

    public List<Album> bestSellingAlbums(int k) {
        if (k <= 0) {
            return Lists.newArrayList();
        } else {
            Collection<Album> albums = dao.loadAll(Album.class);
            ListMultimap<Integer, Album> salesMap = MultimapBuilder.treeKeys().arrayListValues().build();
            for (Album album : albums) {
                salesMap.put(album.getSales(), album);
            }

            List<Album> result = Lists.newArrayList();
            List<Integer> sortedKeys = Lists.newArrayList(salesMap.keySet());
            sortedKeys.sort(Ordering.natural().reverse());

            for (Integer count : sortedKeys) {
                List<Album> list = salesMap.get(count);

                if (result.size() + list.size() >= k) {
                    int newAddition = k - result.size();
                    for (int i = 0; i < newAddition; i++) {
                        result.add(list.get(i));
                    }
                } else {
                    result.addAll(list);
                }
            }

            return result;
        }
    }

    public List<Album> highestRatedAlbums(int k) {
        if (k <= 0) {
            return Lists.newArrayList();
        }else {
            Collection<Album> albums = dao.loadAll(Album.class);
            ListMultimap<Float, Album> ratedMap = MultimapBuilder.treeKeys().arrayListValues().build();
            for (Album album : albums) {
                List<Comment> comments = album.getComment();
                int i = 0;
                int j = 0;
                for (Comment comment : comments) {
                    List<Rating> ratings = comment.getRatings();
                    i += ratings.size();
                    for (Rating rating : ratings) {
                        j += rating.getScore();
                    }
                }
                float average = 0;
                if (i != 0) {
                    average = j / i;
                }
                ratedMap.put(average, album);
            }

            List<Album> result = Lists.newArrayList();
            List<Float> sortedKeys = Lists.newArrayList(ratedMap.keySet());
            sortedKeys.sort(Ordering.natural().reverse());
            for (Float count : sortedKeys) {
                List<Album> list = ratedMap.get(count);

                if (result.size() + list.size() >= k) {
                    int newAddition = k - result.size();
                    for (int i = 0; i < newAddition; i++) {
                        result.add(list.get(i));
                    }
                } else {
                    result.addAll(list);
                }
            }

            return result;
        }
    }

    public List<Musician> highestRatedMusician(int k) {
        if (k <= 0) {
            return Lists.newArrayList();
        }else {
            Collection<Musician> musicians = dao.loadAll(Musician.class);
            ListMultimap<Float, Musician> ratedMap = MultimapBuilder.treeKeys().arrayListValues().build();
            for (Musician musician : musicians) {
                List<Comment> comments = musician.getComment();
                int i = 0;
                int j = 0;
                for (Comment comment : comments) {
                    List<Rating> ratings = comment.getRatings();
                    i += ratings.size();
                    for (Rating rating : ratings) {
                        j += rating.getScore();
                    }
                }
                float average = 0;
                if (i != 0) {
                    average = j / i;
                }
                ratedMap.put(average, musician);
            }

            List<Musician> result = Lists.newArrayList();
            List<Float> sortedKeys = Lists.newArrayList(ratedMap.keySet());
            sortedKeys.sort(Ordering.natural().reverse());
            for (Float count : sortedKeys) {
                List<Musician> list = ratedMap.get(count);

                if (result.size() + list.size() >= k) {
                    int newAddition = k - result.size();
                    for (int i = 0; i < newAddition; i++) {
                        result.add(list.get(i));
                    }
                } else {
                    result.addAll(list);
                }
            }
            return result;
        }
    }
}
