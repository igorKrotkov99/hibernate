package com.example.hibernate;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String departmentName;

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Person> people = new ArrayList<Person>();

    public void addUser(Person person) {
        people.add(person);
    }

    public void removeUser(Person person) {
        people.remove(person);
    }

    public Department() {

    }

    public List<Person> getUsers() {
        return people;
    }

    public void setUsers(List<Person> people) {
        this.people = people;
    }

    public Department(Long id, String departmentName, List<Person> people) {
        this.id = id;
        this.departmentName = departmentName;
        this.people = people;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
