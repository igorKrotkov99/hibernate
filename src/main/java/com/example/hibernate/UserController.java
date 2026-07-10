package com.example.hibernate;

import com.example.hibernate.dto.PersonDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final DepartmentRepository departments;

    public UserController( DepartmentRepository departments, UserService userService) {
        this.departments = departments;
        this.userService = userService;
    }

    @GetMapping("/findAll")
    public void findAll() {
        List<Department> department = departments.findAll(); // попытка получения всех департменов
        for (Department d : department) {
            for(Person person : d.getUsers()){ // при обращении к каждому департменту отправляется еще + 1 запрос
                System.out.println(person); // каждый раз когда пытаемся получить персон отправляется еще + 1 запрос в БД
            }                               // если при получение всех департментов отрпавяется 1 запрос, то для получения персона из каждого департмента нужно отправлять еще один запрос ( то есть 10 департментов = 10 подзапросов + 1 основной )
        }
    }

    @GetMapping("/findById/{id}")
    public Person findById(@PathVariable Long id) throws InterruptedException {
        log.info("finding user by id {} ", id);
        return userService.findById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<PersonDto> create(@RequestBody PersonDto person){
        log.info("Creating user {}", person);
        log.warn("Debug {}", person);
         userService.createUser(person);
         return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public void deleteById(Long id){
        userService.deleteUserByid(id);
    }


}
