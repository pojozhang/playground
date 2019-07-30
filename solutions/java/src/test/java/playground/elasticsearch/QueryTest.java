package playground.elasticsearch;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.intellij.lang.annotations.Language;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;
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
}
