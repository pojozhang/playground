package playground.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class ElasticsearchTestBase {

    RestHighLevelClient client;

    @BeforeAll
    void init() throws IOException {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        client = new RestHighLevelClient(restClientBuilder);
    }

    @AfterAll
    void cleanUp() throws IOException {
        client.close();
    }

    void createIndex(String index, String source) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(index);
        request.source(source, XContentType.JSON);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    void deleteIndex(String index) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        client.indices().delete(request, RequestOptions.DEFAULT);
    }

    void cleanIndex(String index) throws IOException {
        client.deleteByQuery(new DeleteByQueryRequest(index).setQuery(matchAllQuery()), RequestOptions.DEFAULT);
    }

    void index(IndexRequest indexRequest) throws IOException {
        indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        client.index(indexRequest, RequestOptions.DEFAULT);
    }

    SearchResponse query(QueryBuilder queryBuilder) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(queryBuilder);
        searchRequest.source(builder);
        return client.search(searchRequest, RequestOptions.DEFAULT);
    }
}
