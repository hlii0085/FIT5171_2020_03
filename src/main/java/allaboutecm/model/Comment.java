package allaboutecm.model;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

public class Comment extends Entity {

    private URL webURL;

    private List<String> reviews;

    private List<Integer> ratings;

    public Comment () {
        this.webURL = null;
        reviews = Lists.newArrayList();
        ratings = Lists.newArrayList();
    }
    public URL getWebURL() {
        return webURL;
    }

    public void setWebURL(URL webURL) {
        notNull(webURL);
        this.webURL = webURL;
    }

    public List<String> getReview() {
        return reviews;
    }

    public void setReview(List<String> reviews) {
        notNull(reviews);

        if (reviews.size() == 0) {
            throw new IllegalArgumentException("Review list contains no review");
        }

        if (reviews.size() != 0) {
            for (String review : reviews) {
                notBlank(review); }
        }
        this.reviews = reviews;
    }

    public List<Integer> getRatings() {
        return ratings;
    }

    public void setRatings(List<Integer> ratings) {
        notNull(ratings);

        if (ratings.size() == 0) {
            throw new IllegalArgumentException("Rating list contains no review");
        }

        if (ratings.size() != 0) {
            for (Integer rating : ratings) {
                if (rating < 0 || rating > 100) {
                    throw new IllegalArgumentException("Rating should between 0-100");
                }
        }}
        this.ratings = ratings;
    }
}
