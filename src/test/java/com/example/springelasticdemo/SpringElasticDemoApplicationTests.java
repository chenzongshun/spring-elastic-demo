package com.example.springelasticdemo;

import com.example.springelasticdemo.demo.EsOperatorDemo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringElasticDemoApplicationTests {

    @Autowired
    private EsOperatorDemo operatorDemo;

    @Test
    void contextLoads() throws IOException {
        operatorDemo.create();

        operatorDemo.query();

        operatorDemo.update();

        operatorDemo.updateByQuery();

    }

}
