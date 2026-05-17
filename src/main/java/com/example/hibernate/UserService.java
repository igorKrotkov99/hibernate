package com.example.hibernate;

import com.example.hibernate.dto.PersonDto;
import com.example.hibernate.redis.UserRepositoryRedis;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepositoryRedis userRedisRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public UserService(UserRepository userRepository, DepartmentRepository departmentRepository,  UserRepositoryRedis userRedisRepository) {
        this.userRedisRepository = userRedisRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

    public void findAll() {
        List<Department> department = departmentRepository.findAll(); // попытка получения всех департменов
        for (Department d : department) {
            for (Person person : d.getUsers()) { // при обращении к каждому департменту отправляется еще + 1 запрос
                System.out.println(person); // каждый раз когда пытаемся получить персон отправляется еще + 1 запрос в БД
            }                               // если при получение всех департментов отрпавяется 1 запрос, то для получения персона из каждого департмента нужно отправлять еще один запрос ( то есть 10 департментов = 10 подзапросов + 1 основной )
        }
    }

    public Person findById(Long id) throws InterruptedException {
        Person cachedUser = userRedisRepository.getPerson(id);
        if (cachedUser != null) {
            return cachedUser;
        }
        Person person = userRepository.findById(id).orElseThrow(null);
        if(person != null) {
        userRedisRepository.cacheUser(person);
        }
        return person;
    }

    public Person createUser (PersonDto person) {
        Person personEntity = new Person();
        personEntity.setUsername(person.getUsername());
        personEntity.setPassword(person.getPassword());
        Department department = departmentRepository.save(new Department());
        personEntity.setDepartment(department);
        Person result = userRepository.save(personEntity);
        userRedisRepository.cacheUser(result);
        return result;
    }

    public void deleteUserByid(Long id){
        userRepository.deleteById(id);
    }


}
