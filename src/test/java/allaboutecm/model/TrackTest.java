package allaboutecm.model;

import static org.junit.jupiter.api.Assertions.*;
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


class TrackTest {
    private Track track;

    @BeforeEach
    public void setUp() { track = new Track("VANILJE"); }

    @Test
    @DisplayName("Track name cannot be null")
    public void trackNameCannotBeNull() {
        assertThrows(NullPointerException.class, () -> track.setTrackName(null));
    }

    @Test
    @DisplayName("Musician cannot be null")
    public void musicianCannotBeNull() {
        assertThrows(NullPointerException.class, () -> track.setMusician(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("Track name cannot be empty or blank")
    public void trackNameCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> track.setTrackName(arg));
    }

    @Test
    @DisplayName("Length cannot be null")
    public void lengthCannotBeNull() {
        assertThrows(NullPointerException.class, () -> track.setLength(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "sh:09", "8776"})
    @DisplayName("Length should in format such as 03:19,if there is a single number such as 1, you should add 01")
    public void lengthCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> track.setLength(arg));
    }

}