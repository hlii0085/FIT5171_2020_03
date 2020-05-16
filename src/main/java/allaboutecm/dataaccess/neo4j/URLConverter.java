package allaboutecm.dataaccess.neo4j;

import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.net.MalformedURLException;
import java.net.URL;

public class URLConverter implements AttributeConverter<URL, String> {
    @Override
    public String toGraphProperty(URL value) {
        if (null == value) {
            return null;
        }
        return value.toString();
    }

    @Override
    public URL toEntityAttribute(String value) {
        if (null == value) {
            return null;
        }

        try {
            return new URL(value);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Cannot convert string to URL:" + value);
        }
    }
}
