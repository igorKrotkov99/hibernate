package com.example.hibernate;

import com.example.hibernate.dto.PersonDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/persons")
public class UserControllerAnotated {
    private UserServiceAnotated userService;
    private UserRepository userRepository;

    public UserControllerAnotated(UserServiceAnotated userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @PostMapping("/create")
    public ResponseEntity<PersonDto> create(@RequestBody PersonDto person){
        userService.createPerson(person);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/findPersonById")
    public PersonDto findPersonById(@RequestParam Long id){
        return userService.findPerson(id);
    }
}
