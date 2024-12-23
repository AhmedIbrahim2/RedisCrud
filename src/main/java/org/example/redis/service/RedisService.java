package org.example.redis.service;


import org.example.redis.Model.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class RedisService {

    public static final String HASH_KEY = "product";

    @Autowired
    private RedisTemplate<String,Object> template ;


    public product save(product product1) {
        String key = HASH_KEY + product1.getId();
        template.opsForHash().put(key, product1.getId(), product1);
        template.expire(key, Duration.ofMillis(10000)); // Set expiry of 1 hour
        return product1;
    }

    public List<Object> findAll() {
        Set<String> keys = template.keys(HASH_KEY + "*");
        List<Object> allProducts = new ArrayList<>();

        if (keys != null) {
            for (String key : keys) {
                // Retrieve all values from each key
                allProducts.addAll(template.opsForHash().values(key));
            }
        }
        return allProducts;
    }

    public product findById(int id) {
        String key = HASH_KEY + id;
        return (product) template.opsForHash().get(key, id);
    }


    public String deleteById(int id) {
        String key = HASH_KEY + id;
        template.opsForHash().delete(key, id);
        return "deleted Successfully";
    }

    public product update(product product1 , int id) {
        String key = HASH_KEY + id;

        if (Boolean.TRUE.equals(template.hasKey(key))) {
            template.opsForHash().put(key, id, product1);
            return product1;
        }
        throw new RuntimeException("Product not found for id: " + id);
    }
}
