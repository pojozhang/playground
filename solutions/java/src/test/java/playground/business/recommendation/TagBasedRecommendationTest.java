package playground.business.recommendation;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TagBasedRecommendationTest {

    private static final String[] TAGS = new String[]{
            "fruit", "animal", "plant", "life", "forest", "sunrise", "ocean", "apple",
            "google", "music", "marvel", "earth", "coding", "china", "phone", "android",
    };

    private static final Map<String, Class<? extends TaggedItem>> MAPPING = Map.of(
            "article", Article.class,
            "video", Video.class
    );

    private static final int ARTICLE_AMOUNT = 500;
    private static final int VIDEO_AMOUNT = 500;

    private TagBasedRecommendation recommendation;

    @BeforeAll
    void init() throws IOException {
        recommendation = new TagBasedRecommendation(MAPPING);
        recommendation.clean();
        generateArticles(ARTICLE_AMOUNT);
        generateVideos(VIDEO_AMOUNT);
    }

    @AfterAll
    void cleanUp() throws IOException {
        recommendation.close();
    }

    @Test
    void recommend_articles() throws IOException {
        Page<Article> result = recommendation.list(Article.class, TAGS, new Date(), PageRequest.of(0, 10));

        assertEquals(ARTICLE_AMOUNT, result.getTotalElements());
    }

    @Test
    void recommend_articles_and_videos() throws IOException {
        Page<TaggedItem> result = recommendation.list(List.of(Article.class, Video.class), TAGS, new Date(), PageRequest.of(0, 10));

        assertEquals(ARTICLE_AMOUNT + VIDEO_AMOUNT, result.getTotalElements());
    }

    private void generateArticles(int count) throws IOException {
        List<Article> articles = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Article article = new Article();
            article.setId(UUID.randomUUID().toString().substring(0, 8));
            article.setTitle("article#" + article.getId());
            article.setContent("content");
            article.setTags(selectRandomTags());
            article.setCreateTime(new Date());
            articles.add(article);
        }
        recommendation.insert(articles);
    }

    private void generateVideos(int count) throws IOException {
        List<Video> videos = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            Video video = new Video();
            video.setId(UUID.randomUUID().toString().substring(0, 8));
            video.setTitle("video#" + video.getId());
            video.setCover("cover");
            video.setUrl("url");
            video.setTags(selectRandomTags());
            video.setCreateTime(new Date());
            videos.add(video);
        }
        recommendation.insert(videos);
    }

    private String[] selectRandomTags() {
        Random random = new Random();
        List<String> tags = new ArrayList<>(Arrays.asList(TAGS));
        int count = 1 + random.nextInt(tags.size());
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = tags.remove(random.nextInt(tags.size()));
        }
        return result;
    }
}