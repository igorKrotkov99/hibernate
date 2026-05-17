package com.example.hibernate.redis;

import org.apache.catalina.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class TestController {

    TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/{id}")
    public String getId(@PathVariable int id) throws InterruptedException {

        return testService.getStringById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable int id) throws InterruptedException {
        testService.remove(id);
    }

    @PostMapping("/createUser")
    public Boolean createUser(@RequestBody String name) throws InterruptedException {
        return testService.createUser(name);
    }
}
