package com.example.springelasticdemo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author chenjunwei
 * @date 2020-05-12 16:15
 * @description
 */
@Data
@Document(indexName = "test", createIndex = false)
public class User {

    @Id
    private String id;

    private String name;

    private Integer age;
}
