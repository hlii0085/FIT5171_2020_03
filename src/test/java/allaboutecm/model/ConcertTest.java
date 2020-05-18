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


public class ConcertTest {

    private Concert concert;

    @BeforeEach
    public void setUp() {
        concert = new Concert();
    }

    @ParameterizedTest
    @ValueSource(ints = {-2000, -1, 2021, 2022})
    @DisplayName("Year should be a positive number and no more than the current year")
    public void yearPositiveNoMoreThanCurrentYear(int year) {
        assertThrows(IllegalArgumentException.class, () -> concert.setYear(year));
    }

    @Test
    @DisplayName("Month cannot be null")
    public void monthCannotBeNull() {
        assertThrows(NullPointerException.class, () -> concert.setMonth(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"111", " ", "    \t"})
    @DisplayName("Month cannot be empty or blank, should be a word and capitalize the first letter")
    public void monthCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> concert.setMonth(arg));
    }

    @ParameterizedTest
    @ValueSource(ints = {-20, -1, 32, 332})
    @DisplayName("Day should be a positive number and between 1-31")
    public void dayPositive(int day) {
        assertThrows(IllegalArgumentException.class, () -> concert.setDay(day));
    }

    @Test
    @DisplayName("City cannot be null")
    public void cityCannotBeNull() {
        assertThrows(NullPointerException.class, () -> concert.setCity(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("City cannot be empty or blank")
    public void releaseFormatCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> concert.setCity(arg));
    }

    @Test
    @DisplayName("Country cannot be null")
    public void countryCannotBeNull() {
        assertThrows(NullPointerException.class, () -> concert.setCountry(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    \t"})
    @DisplayName("Country cannot be empty or blank")
    public void countryCannotBeEmptyOrBlank(String arg) {
        assertThrows(IllegalArgumentException.class, () -> concert.setCountry(arg));
    }

    @Test
    @DisplayName("There is at least one artist")
    public void artistAtLeastOne() {
        Set<Musician> artists = new HashSet<>();
        assertThrows(IllegalArgumentException.class, () -> concert.setArtists(artists));
    }

    @Test
    @DisplayName("Artist list cannot be null")
    public void artistCannotBeNull() {
        assertThrows(NullPointerException.class, () -> concert.setArtists(null));
    }

    @Test
    @DisplayName("Event list cannot be null")
    public void eventCannotBeNull() {
        assertThrows(NullPointerException.class, () -> concert.setEvents(null));
    }
}
