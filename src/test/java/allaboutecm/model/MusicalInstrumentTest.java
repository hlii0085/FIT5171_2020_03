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


class MusicalInstrumentTest {

    private MusicalInstrument musicalInstrument;

    @BeforeEach
    public void setUp() {
        musicalInstrument = new MusicalInstrument("violin");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("Musical instrument name cannot be empty or blank")
    public void trackNameCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> musicalInstrument.setName(arg));
    }

    @Test
    @DisplayName("Musical instrument name cannot be null")
    public void lengthCannotBeNull() {
        assertThrows(NullPointerException.class, () -> musicalInstrument.setName(null));
    }
}