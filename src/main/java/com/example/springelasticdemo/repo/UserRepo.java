package com.example.springelasticdemo.repo;

import com.example.springelasticdemo.entity.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author chenjunwei
 * @date 2020-05-12 16:16
 * @description
 */
public interface UserRepo extends ElasticsearchRepository<User, String> {

    List<User> findByName(String name);
}
