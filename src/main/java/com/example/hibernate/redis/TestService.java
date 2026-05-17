package com.example.hibernate.redis;

import jakarta.annotation.PostConstruct;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;
import java.util.List;


@Service
public class TestService {

    List<String> list = new ArrayList<>();

    @PostConstruct
    public void init() {
        list.add("Vasya");
        list.add("Petya");
        list.add("Kolya");
        list.add("Nastya");
        list.add("Blohastya");
        list.add("Blohastya2");
    }

    @Cacheable(cacheNames = "products", key = "#id", condition = "#id < 50")
    public String getStringById(int id) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("FETCH FROM DATABASE");
        return list.get(id);
    }

    @CacheEvict(cacheNames = "products", key = "#id")
    public void remove(@PathVariable int id) throws InterruptedException {
        list.remove(id);
    }

    @CachePut(cacheNames = "products", key = "#name")
    public Boolean createUser(String name) {
        return list.add(name);
    }
}
