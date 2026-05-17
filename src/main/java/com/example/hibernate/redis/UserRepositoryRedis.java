package com.example.hibernate.redis;

import com.example.hibernate.Person;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class UserRepositoryRedis {
    private final RedisTemplate<String, Object> redisTemplate;
    private final TestService testService;

    public UserRepositoryRedis(RedisTemplate redisTemplate, TestService testService) {
        this.redisTemplate = redisTemplate;
        this.testService = testService;
    }
    public void cacheUser(Person person) {
        String key = "user:"  + person.getId();
        redisTemplate.opsForValue().set(key, person, Duration.ofMinutes(30));
    }
    public Person getPerson(Long userId) {
        String key = "user:"  + userId;
        return (Person) redisTemplate.opsForValue().get(key);
    }
    public void deleteUserCash(String userId) {
        String key = "user:"  + userId;
        redisTemplate.delete(key);
    }

}
