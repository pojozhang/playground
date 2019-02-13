package playground.business.recommendation;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Video extends TaggedItem {

    private String title;
    private String cover;
    private String url;
    private String[] tags;

    @Override
    public String[] tags() {
        return tags;
    }

    @Override
    public Map<String, Object> source() {
        Map<String, Object> source = new HashMap<>();
        source.put("title", title);
        source.put("cover", cover);
        source.put("url", url);
        return source;
    }
}
