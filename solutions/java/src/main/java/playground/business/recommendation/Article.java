package playground.business.recommendation;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class Article extends TaggedItem {

    private String title;
    private String content;
    private String[] tags;

    @Override
    public String[] tags() {
        return tags;
    }

    @Override
    public Map<String, Object> source() {
        Map<String, Object> source = new HashMap<>();
        source.put("title", title);
        source.put("content", content);
        return source;
    }
}
