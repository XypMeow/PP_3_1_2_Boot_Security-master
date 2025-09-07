package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        System.out.println("🔍 Пытаемся найти пользователя: '" + name + "'");
        User user = userRepository.findByName(name);
        if (user == null) {
            System.out.println("Пользователь не найден");
            throw new UsernameNotFoundException("User not found: " + name);
        }
        System.out.println("Найден: " + user.getName() + ", пароль: " + user.getPassword());
        return user;
    }

    @Autowired
    private RoleRepository roleRepository;

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }
}