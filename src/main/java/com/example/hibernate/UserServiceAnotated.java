package com.example.hibernate;

import com.example.hibernate.dto.PersonDto;
import com.example.hibernate.redis.UserRepositoryRedis;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceAnotated {

    private final UserRepository userRepository;
    private final UserRepositoryRedis userRepositoryRedis;
    private final DepartmentRepository departmentRepository;

    public UserServiceAnotated(UserRepository userRepository, UserRepositoryRedis userRepositoryRedis, DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.userRepositoryRedis = userRepositoryRedis;
        this.departmentRepository = departmentRepository;
    }


    @Cacheable(value = "users", key = "#id")
    public PersonDto findPerson(Long id) {
        return userRepository.findById(id)
                .map(person -> new PersonDto(
                        person.getPassword(),
                        person.getUsername()
                ))
                .orElse(null);
    }

    public void deletePerson(Person person){
        userRepository.delete(person);
    }

    public void createPerson(PersonDto person){
        Person personEntity = new Person();
        personEntity.setUsername(person.getUsername());
        personEntity.setPassword(person.getPassword());
        Department department = departmentRepository.save(new Department());
        personEntity.setDepartment(department);
        userRepository.save(personEntity);
    }


}
