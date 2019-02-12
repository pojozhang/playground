package playground.business;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Closeable;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.elasticsearch.common.lucene.search.function.CombineFunction.SUM;
import static org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.ScoreMode.MULTIPLY;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.randomFunction;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.weightFactorFunction;

public class ArticleRecommendation implements Closeable {

    private final String index;
    private final RestHighLevelClient client;

    public ArticleRecommendation(String index) {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        this.client = new RestHighLevelClient(restClientBuilder);
        this.index = index;
    }

    public void createArticles(List<Article> articles) throws IOException {
        BulkRequest request = new BulkRequest();
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        articles.forEach(article -> request.add(article.buildIndexRequest(this.index)));
        this.client.bulk(request, RequestOptions.DEFAULT);
    }

    public Page<Article> recommend(String[] tags, Date date, Pageable pageable) throws IOException {
        Article request = new Article();
        request.setTags(tags);
        request.setCreateTime(date);

        SearchResponse response = this.client.search(request.buildRecommendationRequest(this.index, pageable), RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        return new PageImpl<>(
                Arrays.stream(searchHits.getHits())
                        .map(Article::parse).collect(Collectors.toList()),
                pageable, searchHits.getTotalHits());
    }

    public void clean() throws IOException {
        GetIndexRequest request = new GetIndexRequest();
        request.indices(index);
        if (this.client.indices().exists(request, RequestOptions.DEFAULT)) {
            this.client.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
        }
    }

    @Override
    public void close() throws IOException {
        if (this.client != null) {
            this.client.close();
        }
    }

    @Getter
    @Setter
    public static class Article {
        private String id;
        private String title;
        private String content;
        private String[] tags;
        private Date createTime;

        private IndexRequest buildIndexRequest(String index) {
            Map<String, Object> source = new HashMap<>();
            source.put("title", title);
            source.put("content", content);
            source.put("tags", tags);
            source.put("createTime", createTime);
            return new IndexRequest(index, index, id).source(source);
        }

        private SearchRequest buildRecommendationRequest(String index, Pageable pageable) {
            SearchRequest searchRequest = new SearchRequest(index);
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = boolQuery().must(matchAllQuery())
                    .should(rangeQuery("createTime").gte(this.createTime).boost(1000))
                    .minimumShouldMatch(0);

            if (ArrayUtils.isEmpty(tags)) {
                searchSourceBuilder.query(QueryBuilders.functionScoreQuery(boolQueryBuilder));
            } else {
                searchSourceBuilder.query(QueryBuilders.functionScoreQuery(boolQueryBuilder, tagFilterFunctionBuilder(tags))
                        .scoreMode(MULTIPLY).boostMode(SUM));
            }
            searchSourceBuilder.from((int) pageable.getOffset()).size(pageable.getPageSize());
            searchRequest.source(searchSourceBuilder);
            return searchRequest;
        }

        private FunctionScoreQueryBuilder.FilterFunctionBuilder[] tagFilterFunctionBuilder(String[] tags) {
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[tags.length + 1];
            for (int i = 0; i < tags.length; i++) {
                builders[i] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                        matchQuery("tags", tags[i]),
                        weightFactorFunction(2));
            }
            builders[tags.length] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(randomFunction());
            return builders;
        }

        private static Article parse(SearchHit searchHit) {
            Article article = JSON.parseObject(searchHit.getSourceAsString(), Article.class);
            article.setId(searchHit.getId());
            return article;
        }
    }
}
