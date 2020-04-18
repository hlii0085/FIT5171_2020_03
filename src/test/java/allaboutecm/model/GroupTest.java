package allaboutecm.model;

import static org.junit.jupiter.api.Assertions.*;
import com.google.common.collect.Lists;
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


class GroupTest {
    private Group group;

    @BeforeEach
    public void setUp() {
        group = new Group("Jan Garbarek Quartet");
    }

    @Test
    @DisplayName("Group name cannot be null")
    public void groupNameCannotBeNull() {
        assertThrows(NullPointerException.class, () -> group.setName(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("Group name cannot be empty or blank")
    public void groupNameCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> group.setName(arg));
    }

    @Test
    @DisplayName("Musician group cannot be null")
    public void musicianGroupCannotBeNull() {
        assertThrows(NullPointerException.class, () -> group.setMusicians(null));
    }

    @Test
    @DisplayName("There should be at least two musicians")
    public void musicianAtLeastTwo() {
        Set<Musician> musicians = new HashSet<>();
        Musician mmm = new Musician("zhoujielun");
        musicians.add(mmm);
        assertThrows(IllegalArgumentException.class, () -> group.setMusicians(musicians));
    }

    @Test
    @DisplayName("Element in album list cannot be null")
    public void elementInAlbumListCannotBeNull() {
        assertThrows(NullPointerException.class, () -> group.setAlbums(null));
    }

    @Test
    @DisplayName("There should be at least one album")
    public void albumAtLeastOne() {
        Set<Album> albums = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> group.setAlbums(albums));
    }
}