package allaboutecm.model;

import static org.junit.jupiter.api.Assertions.*;
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


class MusicianInstrumentTest {

    private MusicianInstrument musicianInstrument;

    @BeforeEach
    public void setUp() {
        Musician musician = new Musician("Keith Jarrett ");
        MusicalInstrument musicalInstrument = new MusicalInstrument("Piano");
        musicianInstrument = new MusicianInstrument(musician, musicalInstrument);
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