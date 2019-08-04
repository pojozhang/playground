package playground.elasticsearch;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryTest extends ElasticsearchTestBase {

    private static final String INDEX = "query_test_index";

    @Override
    @BeforeAll
    void init() throws IOException {
        super.init();
        @Language("JSON")
        String source = "{\n" +
                "  \"mappings\": {\n" +
                "    \"properties\": {\n" +
                "      \"text_field\": {\n" +
                "        \"type\": \"text\",\n" +
                "        \"analyzer\": \"standard\"\n" +
                "      },\n" +
                "      \"keyword_field\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      }\n" +
                "    }\n" +
                "  " +
                "}\n" +
                "}";
        createIndex(INDEX, source);
    }

    @AfterEach
    void tearDown() throws IOException {
        cleanIndex(INDEX);
    }

    @Override
    @AfterAll
    void cleanUp() throws IOException {
        deleteIndex(INDEX);
        super.cleanUp();
    }

    @Test
    void term_query_should_find_documents_that_contain_the_exact_term_in_a_field() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("keyword_field", "hello world");
        index(indexRequest);

        SearchResponse response = query(termQuery("keyword_field", "hello world"));

        assertEquals(1, response.getHits().getTotalHits().value);
    }

    @Test
    void term_query_should_not_find_documents_that_not_contain_the_exact_term_in_a_analyzed_field() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("text_field", "hello world");
        index(indexRequest);

        SearchResponse response = query(termQuery("text_field", "hello world"));

        assertEquals(0, response.getHits().getTotalHits().value);
    }

    @Test
    void term_query_should_find_documents_that_contain_the_exact_term_in_a_analyzed_field() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("text_field", "hello world");
        index(indexRequest);

        SearchResponse response = query(termQuery("text_field", "hello"));

        assertEquals(1, response.getHits().getTotalHits().value);
    }

    @Test
    void match_query_will_analyze_the_text_before_query() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("text_field", "hello, my world");
        index(indexRequest);

        SearchResponse response = query(matchQuery("text_field", "hello world"));

        assertEquals(1, response.getHits().getTotalHits().value);
    }

    @Test
    void match_phrase_query_should_match_terms_in_exact_order() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("text_field", "I like driving and reading");
        index(indexRequest);

        SearchResponse response = query(matchPhraseQuery("text_field", "I like driving"));

        assertEquals(1, response.getHits().getTotalHits().value);
    }

    @Test
    void match_phrase_query_should_not_match_if_the_phrase_is_not_contained() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("text_field", "I like driving and reading");
        index(indexRequest);

        SearchResponse response = query(matchPhraseQuery("text_field", "I like reading"));

        assertEquals(0, response.getHits().getTotalHits().value);
    }

    @Test
    void match_phrase_query_should_match_terms_when_slop_is_set() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("text_field", "I like driving and reading");
        index(indexRequest);

        SearchResponse response = query(matchPhraseQuery("text_field", "I like reading").slop(2));

        assertEquals(1, response.getHits().getTotalHits().value);
    }

    @Test
    void match_phrase_prefix_query_should_match_prefix_of_last_term() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("text_field", "I like driving and reading");
        index(indexRequest);

        SearchResponse response = query(matchPhrasePrefixQuery("text_field", "I like d"));

        assertEquals(1, response.getHits().getTotalHits().value);
    }

    @Test
    void match_phrase_prefix_query_should_match_prefix_of_last_term_when_slop_is_set() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("text_field", "I like driving and reading");
        index(indexRequest);

        SearchResponse response = query(matchPhrasePrefixQuery("text_field", "I like r").slop(2));

        assertEquals(1, response.getHits().getTotalHits().value);
    }

    @Test
    void wildcard_query_should_return_documents_that_contain_terms_matching_a_wildcard_pattern() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("text_field", "I like driving and reading",
                        "keyword_field", "I like driving and reading");
        index(indexRequest);

        SearchResponse response1 = query(wildcardQuery("text_field", "lik*"));
        SearchResponse response2 = query(wildcardQuery("keyword_field", "I lik*"));

        assertEquals(1, response1.getHits().getTotalHits().value);
        assertEquals(1, response2.getHits().getTotalHits().value);
    }

    @Test
    void wildcard_query_will_not_analyze_the_query_string() throws IOException {
        IndexRequest indexRequest = new IndexRequest(INDEX)
                .source("text_field", "I like driving and reading");
        index(indexRequest);

        SearchResponse response = query(wildcardQuery("text_field", "I lik*"));

        assertEquals(0, response.getHits().getTotalHits().value);
    }
}
