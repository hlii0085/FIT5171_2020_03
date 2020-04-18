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


class CommentTest {

    private Comment comment;

    @BeforeEach
    public void setUp() {
        comment = new Comment();
    }

    @Test
    @DisplayName("Web URL cannot be null")
    public void webURLCannotBeNull() {
        assertThrows(NullPointerException.class, () -> comment.setWebURL(null));
    }

    @Test
    @DisplayName("Element in review list cannot be null")
    public void elementInReviewlistCannotBeNull() {
        List<String> review = new ArrayList<>();
        review.add(null);
        assertThrows(NullPointerException.class, () -> comment.setReview(review));
    }

    @Test
    @DisplayName("Element in review list cannot be empty or blank")
    public void elementInReviewlistCannotBeEmptyOrBlank() {
        List<String> review = new ArrayList<>();
        review.add("qinghuaci");
        review.add("niuzaihenmang");
        review.add("  ");
        review.add("    \t");
        assertThrows(IllegalArgumentException.class, () -> comment.setReview(review));
    }

    @Test
    @DisplayName("Element in rating list should between 0-100")
    public void elementInRatinglistOutOfBoundary() {
        List<Integer> ratings = new ArrayList<>();
        ratings.add(-1);
        ratings.add(101);
        ratings.add(3000);
        ratings.add(-200);
        assertThrows(IllegalArgumentException.class, () -> comment.setRatings(ratings));
    }

    @Test
    @DisplayName("Ratings cannot be null")
    public void ratingCannotBeNull() {
        assertThrows(NullPointerException.class, () -> comment.setRatings(null));
    }
}