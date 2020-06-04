package allaboutecm.model;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertThrows;


class MusicianInstrumentTest {

    private MusicianInstrument musicianInstrument;

    @BeforeEach
    public void setUp() {
        Musician musician = new Musician("Keith Jarrett ");
        MusicalInstrument musicalInstrument = new MusicalInstrument("Piano");
        musicianInstrument = new MusicianInstrument(musician, Sets.newHashSet(musicalInstrument));
    }

    @Test
    @DisplayName("Musical instrument cannot be null")
    public void musicalInstrumentCannotBeNull() {
        assertThrows(NullPointerException.class, () -> musicianInstrument.setMusicalInstrument(null));
    }


    @Test
    @DisplayName("Musician cannot be null")
    public void musicianCannotBeNull() {
        assertThrows(NullPointerException.class, () -> musicianInstrument.setMusician(null));
    }

}