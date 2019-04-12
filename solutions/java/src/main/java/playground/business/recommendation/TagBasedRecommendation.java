package playground.business.recommendation;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.elasticsearch.common.lucene.search.function.CombineFunction.SUM;
import static org.elasticsearch.common.lucene.search.function.FunctionScoreQuery.ScoreMode.MULTIPLY;
import static org.elasticsearch.index.query.QueryBuilders.*;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.randomFunction;
import static org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders.weightFactorFunction;

public class TagBasedRecommendation {

    private static final String FIELD_TAGS = "tags";
    private final RestHighLevelClient client;
    private final Map<String, Class<? extends TaggedItem>> indexItemMapping;
    private final Map<Class<? extends TaggedItem>, String> itemIndexMapping;

    TagBasedRecommendation(Map<String, Class<? extends TaggedItem>> mapping) {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        this.client = new RestHighLevelClient(restClientBuilder);
        this.indexItemMapping = mapping;
        this.itemIndexMapping = mapping.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    @SuppressWarnings("unchecked")
    public <T extends TaggedItem> Page<T> list(Class<T> itemClass, String[] tags, Date decay, Pageable pageable) throws IOException {
        return (Page<T>) this.list(List.of(itemClass), tags, decay, pageable);
    }

    public Page<TaggedItem> list(List<Class<? extends TaggedItem>> itemClasses, String[] tags, Date decay, Pageable pageable) throws IOException {
        SearchResponse response = this.client.search(buildRecommendationRequest(indices(itemClasses), tags, decay, pageable), RequestOptions.DEFAULT);
        SearchHits searchHits = response.getHits();
        return new PageImpl<>(
                Arrays.stream(searchHits.getHits()).map(this::parse).collect(Collectors.toList()),
                pageable, searchHits.getTotalHits().value);
    }

    private String[] indices(List<Class<? extends TaggedItem>> itemClasses) {
        String[] indices = new String[itemClasses.size()];
        for (int i = 0; i < itemClasses.size(); i++) {
            indices[i] = itemIndexMapping.get(itemClasses.get(i));
        }
        return indices;
    }

    private SearchRequest buildRecommendationRequest(String[] indices, String[] tags, Date decay, Pageable pageable) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().from((int) pageable.getOffset())
                .size(pageable.getPageSize());

        BoolQueryBuilder boolQueryBuilder = boolQuery().must(matchAllQuery())
                .should(rangeQuery("createTime").gte(decay).boost(1000))
                .minimumShouldMatch(0);

        searchSourceBuilder.query(QueryBuilders.functionScoreQuery(boolQueryBuilder, tagFilterFunctionBuilder(tags))
                .scoreMode(MULTIPLY).boostMode(SUM));

        return new SearchRequest(indices).source(searchSourceBuilder);
    }

    private FunctionScoreQueryBuilder.FilterFunctionBuilder[] tagFilterFunctionBuilder(String[] tags) {
        FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[tags.length + 1];
        for (int i = 0; i < tags.length; i++) {
            builders[i] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                    matchQuery(FIELD_TAGS, tags[i]),
                    weightFactorFunction(2));
        }
        builders[tags.length] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(randomFunction());
        return builders;
    }

    private TaggedItem parse(SearchHit searchHit) {
        Class<? extends TaggedItem> itemClass = indexItemMapping.get(searchHit.getIndex());
        TaggedItem item = JSON.parseObject(searchHit.getSourceAsString(), itemClass);
        item.setId(searchHit.getId());
        return item;
    }

    public void insert(TaggedItem item) throws IOException {
        this.client.index(buildIndexRequest(item), RequestOptions.DEFAULT);
    }

    public void insert(Iterable<? extends TaggedItem> items) throws IOException {
        BulkRequest request = new BulkRequest();
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        items.forEach(item -> request.add(buildIndexRequest(item)));
        this.client.bulk(request, RequestOptions.DEFAULT);
    }

    private IndexRequest buildIndexRequest(TaggedItem item) {
        String index = itemIndexMapping.get(item.getClass());
        Map<String, Object> source = item.source();
        source.put("id", item.getId());
        source.put("createTime", item.getCreateTime());
        source.put(FIELD_TAGS, item.tags());
        return new IndexRequest(index).id(item.getId()).source(source);
    }

    void clean() throws IOException {
        for (String index : itemIndexMapping.values()) {
            GetIndexRequest request = new GetIndexRequest(index);
            if (this.client.indices().exists(request, RequestOptions.DEFAULT)) {
                this.client.indices().delete(new DeleteIndexRequest(index), RequestOptions.DEFAULT);
            }
        }
    }

    void close() throws IOException {
        if (this.client != null) {
            this.client.close();
        }
    }
}
