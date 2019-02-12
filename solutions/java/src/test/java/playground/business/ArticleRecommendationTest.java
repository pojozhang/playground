package playground.business;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ArticleRecommendationTest {

    private static final String[] TAGS = new String[]{
            "fruit", "animal", "plant", "life", "forest", "sunrise", "ocean", "apple",
            "google", "music", "marvel", "earth", "coding", "china", "phone", "android",
    };

    private ArticleRecommendation recommendation;

//    @BeforeAll
    void init() {
        recommendation = new ArticleRecommendation();
    }

//    @AfterAll
    void cleanUp() throws IOException {
        recommendation.close();
    }

//    @Test
    void recommend_articles_by_tag() throws IOException {
        recommendation.clean();

        final int articlesPerTag = 5;
        for (int i = 0; i < TAGS.length; i++) {
            List<ArticleRecommendation.Article> articles = new ArrayList<>();
            for (int j = 1; j <= articlesPerTag; j++) {
                ArticleRecommendation.Article article = new ArticleRecommendation.Article();
                article.setId(String.valueOf(i * articlesPerTag + j));
                article.setTitle("article " + article.getId());
                article.setContent("content");
                article.setTags(new String[]{TAGS[i], i + 1 == TAGS.length ? TAGS[0] : TAGS[i + 1]});
                article.setCreateTime(new Date());
                articles.add(article);
            }
            recommendation.createArticles(articles);
        }
    }

//    @Test
    void special() throws IOException {
        ArticleRecommendation.Article article = new ArticleRecommendation.Article();
        article.setId("article 10086");
        article.setTitle("article " + article.getId());
        article.setContent("content");
        article.setTags(TAGS);
        article.setCreateTime(new Date(1549382400000L));

        recommendation.createArticle(article);
    }

//    @Test
    void q() throws IOException {
        Page<ArticleRecommendation.Article> articles = recommendation.recommend(TAGS,
                DateUtils.addDays(DateUtils.truncate(new Date(), Calendar.DATE), -5),
                PageRequest.of(0, 10));
        System.out.println("sss");
    }

    void generateArticles(int count) throws IOException {
        for (int i = 1; i <= count; i++) {
            List<ArticleRecommendation.Article> articles = new ArrayList<>();
            ArticleRecommendation.Article article = new ArticleRecommendation.Article();
            article.setId(UUID.randomUUID().toString().substring(0, 8));
            article.setTitle("article #" + article.getId());
            article.setContent("content");
            article.setTags(randomTags());
            article.setCreateTime(new Date());
            articles.add(article);
            recommendation.createArticles(articles);
        }
    }

    String[] randomTags() {
        Random random = new Random();
        List<String> tags = Arrays.asList(TAGS);
        int count = 1 + random.nextInt(tags.size());
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = tags.remove(random.nextInt(tags.size()));
        }
        return result;
    }

}