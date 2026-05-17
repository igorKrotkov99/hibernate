package com.example.hibernate.service;

import com.example.hibernate.*;
import com.example.hibernate.redis.UserRepositoryRedis;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    UserRepositoryRedis userRepositoryRedis;

    @InjectMocks
    private UserService userService;

    public Person person = new Person();
    public Department department = new Department();

    @BeforeEach
    public void setUp(){

        person.setUsername("admin");
        person.setPassword("admin");
        List<Person> persons = new ArrayList<Person>();
        department.addUser(person);
        persons.add(person);
        System.out.println("1");
    }
    @Test
    public void myFirstTest(){

    }
    @Test
    public void findAllPersons(){
        List<Department> department = new ArrayList<Department>();
        when(departmentRepository.findAll()).thenReturn(department);
        /// departmentRepository.findAll();
        userService.findAll();
        verify(departmentRepository, times(1)).findAll();
    }
    @Test
    public void findById() throws InterruptedException {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(person));
        Person foundedPerson = userService.findById(anyLong());
        verify(userRepository, times(1)).findById(anyLong());
        Assertions.assertNotNull(foundedPerson);
        Assertions.assertEquals(foundedPerson.getUsername(), person.getUsername());
    }

    @Test
    public void createUserTest(){
        when(userRepository.save(person)).thenReturn((person));
        Person createdPerson = userRepository.save(person);
        verify(userRepository, times(1)).save(createdPerson);
        Assertions.assertNotNull(createdPerson);
    }
}

