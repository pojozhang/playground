package playground.business;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArticleRecommendationTest {

    private static final String INDEX = "article";
    private static final String[] TAGS = new String[]{
            "fruit", "animal", "plant", "life", "forest", "sunrise", "ocean", "apple",
            "google", "music", "marvel", "earth", "coding", "china", "phone", "android",
    };

    private ArticleRecommendation recommendation;

    @BeforeAll
    void init() throws IOException {
        recommendation = new ArticleRecommendation(INDEX);
        recommendation.clean();
    }

    @AfterAll
    void cleanUp() throws IOException {
        recommendation.close();
    }

    @Test
    void recommend_articles_by_tag() throws IOException {
        generateArticles(1000);

        Page<ArticleRecommendation.Article> result = recommendation.recommend(TAGS, new Date(), PageRequest.of(0, 10));

        assertEquals(1000, result.getTotalElements());
    }

    private void generateArticles(int count) throws IOException {
        List<ArticleRecommendation.Article> articles = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            ArticleRecommendation.Article article = new ArticleRecommendation.Article();
            article.setId(UUID.randomUUID().toString().substring(0, 8));
            article.setTitle("article#" + article.getId());
            article.setContent("content");
            article.setTags(selectRandomTags());
            article.setCreateTime(new Date());
            articles.add(article);
        }
        recommendation.createArticles(articles);
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