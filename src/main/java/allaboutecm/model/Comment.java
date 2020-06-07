package allaboutecm.model;


import allaboutecm.dataaccess.neo4j.URLConverter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jdk.nashorn.internal.objects.annotations.Property;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.net.URL;
import java.util.List;
import java.lang.*;
import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@NodeEntity
public class Comment extends Entity {

    @Convert(URLConverter.class)
    @Property(name="webURL")
    private URL webURL;

    @Relationship(type="reviews")
    private List<String> reviews;

    @Relationship(type="ratings")
    private List<Rating> ratings;

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

        for (String review : reviews) {
            if (review.length() > 500) {
                throw new IllegalArgumentException("The length of the review should within 500");
            }
        }

        if (reviews.isEmpty()) {
            throw new IllegalArgumentException("Review list contains no review");
        }

        if (!reviews.isEmpty()) {
            for (String review : reviews) {
                notBlank(review); }
        }
        this.reviews = reviews;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        notNull(ratings);

        if (ratings.isEmpty()) {
            throw new IllegalArgumentException("Rating list contains no review");
        }

        this.ratings = ratings;
    }
}
