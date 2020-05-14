package com.example.springelasticdemo.demo;

import com.example.springelasticdemo.entity.User;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenjunwei
 * @date 2020-05-14 10:19
 * @description
 */
@Component
public class EsOperatorDemo {

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    private RestHighLevelClient highLevelClient;

    public void update() {
        IndexCoordinates test = IndexCoordinates.of("test-*");
        UpdateQuery build = UpdateQuery.builder("bbb").withDocument(Document.create().append("age", "2")).build();
        UpdateQuery.builder("bbb");

        UpdateResponse update = elasticsearchOperations.update(build, test);
        System.out.println(update.getResult());
    }
    public void updateByQuery() throws IOException {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("age", 18);
        ScriptType type = ScriptType.INLINE;
        String lang = "painless";
        String code = "ctx._source.age=params.age";
        Script script = new Script(type, lang, code, params);
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest("test-*").setScript(script)
                                                                                      .setQuery(QueryBuilders.termQuery("id", "bbb"));
        BulkByScrollResponse response = highLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);

        System.out.println(response.getUpdated());
    }

    public void create() {
        User user = new User();
        user.setId("abc");
        user.setName("hello world");

        IndexCoordinates test = IndexCoordinates.of("test-20200512");
        elasticsearchOperations.save(user, test);
    }

    public void query() {
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "java");
        BoolQueryBuilder filter = QueryBuilders.boolQuery().filter(termQueryBuilder);
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(filter);

        IndexCoordinates coordinates = IndexCoordinates.of("test-*");
        SearchHits<User> search = elasticsearchOperations.search(nativeSearchQuery, User.class, coordinates);
        System.out.println(search.getSearchHits());
    }

}
