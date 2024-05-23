package ru.itmentor.spring.boot_security.demo.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {
    private final UserService userService;


    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;

    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getList());
    }


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {

if (userService.create(user)){
    return new ResponseEntity<User>(user, HttpStatus.OK);
}
else {
    return new ResponseEntity<User>(user, HttpStatus.INTERNAL_SERVER_ERROR);
}
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {


        if (userService.delete(id)){
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        }
        else return new ResponseEntity<>("User not deleted", HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody User newUser) {

        if (userService.updateUser(id, newUser)){
            return new ResponseEntity<User>(newUser, HttpStatus.OK);
        }
        else return new ResponseEntity<User>(newUser, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
