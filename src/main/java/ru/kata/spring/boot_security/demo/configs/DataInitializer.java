// src/main/java/ru/kata/spring/boot_security/demo/configs/DataInitializer.java

package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.services.PersonService;

import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Role roleUser = getOrCreateRole("ROLE_USER");
        Role roleAdmin = getOrCreateRole("ROLE_ADMIN");

        createUserIfNotExists("user", 25, "user", Set.of(roleUser));
        createUserIfNotExists("admin", 30, "admin", Set.of(roleAdmin, roleUser));
    }

    private Role getOrCreateRole(String roleName) {
        Role role = personService.findRoleByName(roleName);
        if (role == null) {
            role = new Role(roleName);
            personService.saveRole(role);
        }
        return role;
    }

    private void createUserIfNotExists(String name, int age, String password, Set<Role> roles) {
        if (!userRepository.existsByName(name)) {
            User user = new User();
            user.setName(name);
            user.setAge(age);
            user.setPassword(passwordEncoder.encode(password));
            user.setRoles(roles);
            personService.save(user);
            System.out.println("✅ Создан пользователь: " + name);
        } else {
            System.out.println("🟨 Пользователь уже существует: " + name);
        }
    }
}