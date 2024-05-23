package ru.itmentor.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.configs.PasswordEncoderConfig;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService{
    private final UserRepository userRepository;
    private final RoleService roleService;

    private final PasswordEncoderConfig passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoderConfig passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean create(User user) {
        if (user.getUsername().equals("") | user.getPassword().equals("")) {
            throw new UsernameNotFoundException("User не имеет пароля и логина!");
        } else {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public List<User> getUserAndRoles() {
        return userRepository.findAll();
    }




    @Override
    public User setRoleByUser (User user, String[] roles) {
        user.setRoles(roleService.getRoleByName(roles));
        return user;
    }


    @Override
    public List<User> getUserByUsername(String name) {
        List<User> list = new ArrayList<>();
        list.add(userRepository.findByUsername(name));
        return list;
    }

    @Override
    public List<User> getList() {
        return userRepository.findAll();
    }

    @Override
    public boolean updateUser(Long id, User newUser) {
        User existingUser = userRepository.findById(id).orElse(null);
        existingUser.setUsername(newUser.getUsername());
        String encodedPassword = passwordEncoder.passwordEncoder().encode(newUser.getPassword());
        existingUser.setPassword(encodedPassword);
        existingUser.setRoles(newUser.getRoles());
        userRepository.save(existingUser);
        return true;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username);
        return userDetails;
    }
}
