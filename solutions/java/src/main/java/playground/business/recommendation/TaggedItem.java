package playground.business.recommendation;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
public abstract class TaggedItem {

    protected String id;
    protected Date createTime;

    public abstract String[] tags();

    public abstract Map<String, Object> source();
}
