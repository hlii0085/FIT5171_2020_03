package allaboutecm.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;


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
    @DisplayName("Review list cannot be empty")
    public void reviewListCannotBeEmpty() {
        List<String> review = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> comment.setReview(review));
    }

    @Test
    @DisplayName("Rating list cannot be empty")
    public void ratingListCannotBeEmpty() {
        List<Rating> rating = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> comment.setRatings(rating));
    }

    @Test
    @DisplayName("Ratings cannot be null")
    public void ratingCannotBeNull() {
        assertThrows(NullPointerException.class, () -> comment.setRatings(null));
    }
}