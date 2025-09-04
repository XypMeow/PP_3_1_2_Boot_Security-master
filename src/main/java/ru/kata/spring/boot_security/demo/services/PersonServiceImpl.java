package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;

import java.util.List;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public PersonServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> index() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User show(int id) {
        return userRepository.findById((long) id).orElse(null);
    }

    @Override
    public void save(User person) {
        userRepository.save(person);
    }

    @Override
    public void update(int id, User person) {
        person.setId((long) id);
        userRepository.save(person);
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById((long) id);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}