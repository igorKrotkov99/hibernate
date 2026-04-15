package com.example.hibernate.service;


import com.example.hibernate.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private DepartmentRepository departmentRepository;
    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private Person person;
    private Department department;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        person = new Person();
        person.setId(1L);
        person.setUsername("Ivan");
        person.setPassword("password");

        department = new Department();
        department.setDepartmentName("Department");
        department.setId(1L);
    }
    @Test
    public void create_ShouldCreateUser() throws Exception {
        when(userService.createUser(any(Person.class))).thenReturn(person);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/create")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdTest() throws Exception {

        when(userService.findById(anyLong())).thenReturn(person);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/findById/{id}", 1L)
                        .param("id", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}