package com.example.springelasticdemo;

import com.example.springelasticdemo.entity.User;
import com.example.springelasticdemo.repo.UserRepo;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

@SpringBootTest
class SpringElasticDemoApplicationTests {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    @Test
    void contextLoads() {

        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("age", 10);

        userRepo.search(matchQuery);
        IndexCoordinates indexCoordinates = elasticsearchOperations.getIndexCoordinatesFor(User.class);


        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(matchQuery);
        SearchHits<User> search = elasticsearchOperations.search(nativeSearchQuery, User.class, indexCoordinates);
        System.out.println(search.getSearchHits());

    }

}
