package allaboutecm.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("Ratings cannot be null")
    public void ratingCannotBeNull() {
        assertThrows(NullPointerException.class, () -> comment.setRatings(null));
    }
}