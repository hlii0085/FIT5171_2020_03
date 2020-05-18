package allaboutecm.model;

import allaboutecm.dataaccess.neo4j.URLConverter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import jdk.nashorn.internal.objects.annotations.Property;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.net.URL;
import java.time.Year;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.apache.commons.lang3.Validate.notBlank;
import static org.apache.commons.lang3.Validate.notNull;

@NodeEntity
public class Rating {

    @Property(name="score")
    private int score;

    @Property(name="source")
    private String source;

    public Rating (int score, String source) {
        notNull(score);
        notNull(source);
        notBlank(source);
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score should between 0-100");
        } else {
            this.score = score;
        }

        this.score = score;
        this.source = source;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        notNull(score);
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException("Score should between 0-100");
        } else {
            this.score = score;
        }
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        notNull(source);
        notBlank(source);
    }
}
