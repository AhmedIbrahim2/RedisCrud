package org.example.redis.controller;


import org.example.redis.Model.product;
import org.example.redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/redis")
public class RedisController {


    @Autowired
    public RedisService redisService;




    @PostMapping("/add")
    public ResponseEntity<product> save (@RequestBody product product) {
        try {
        return new ResponseEntity<>(redisService.save(product), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Object>> getAll () {
        try {
            return new ResponseEntity<>(redisService.findAll(),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<product> getAll (@PathVariable int id) {
        try {
            return new ResponseEntity<>(redisService.findById(id),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> delete (@PathVariable int id) {
        try {
            return new ResponseEntity<>(redisService.deleteById(id),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<product> update (@PathVariable int id, @RequestBody product product) {
        try {
            return new ResponseEntity<>(redisService.update(product , id),HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
