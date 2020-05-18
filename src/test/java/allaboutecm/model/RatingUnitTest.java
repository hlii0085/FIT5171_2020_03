package allaboutecm.model;

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


public class RatingUnitTest {
    private Rating rating;

    @BeforeEach
    public void setUp() {
        rating = new Rating(99, "woyebuzhidaoxieshenme");
    }

    @ParameterizedTest
    @ValueSource(ints = {-2000, -1, 2021, 2022})
    @DisplayName("Rating score should be a positive number and between 0-100")
    public void scorePositiveNoMoreThanHundred(int score) {
        assertThrows(IllegalArgumentException.class, () -> rating.setScore(score));
    }

    @Test
    @DisplayName("Rating source cannot be null")
    public void ratingSourceCannotBeNull() {
        assertThrows(NullPointerException.class, () -> rating.setSource(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("Rating source cannot be empty or blank")
    public void sourceCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> rating.setSource(arg));
    }
}
