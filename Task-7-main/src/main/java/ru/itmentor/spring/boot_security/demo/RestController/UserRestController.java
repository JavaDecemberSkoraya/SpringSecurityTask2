package ru.itmentor.spring.boot_security.demo.restController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
@GetMapping()
    public ResponseEntity<User> getUser(){
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    Object principal =auth.getPrincipal();
    User user =(User) principal;
return ResponseEntity.ok(user);
}

}
