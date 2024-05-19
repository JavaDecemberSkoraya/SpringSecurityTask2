package ru.itmentor.spring.boot_security.demo.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public ModelAndView getAllUsers(){
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("users", userService.getUserAndRoles());
        return modelAndView;
    }


    @GetMapping("/new")
    public ModelAndView saveUserForm() {
        ModelAndView modelAndView = new ModelAndView("new"); // Имя представления new_user.html
        modelAndView.addObject("roles", roleService.getAllRoles());
        modelAndView.addObject("user", new User());
        return modelAndView;
    }


    @PostMapping
    public ModelAndView createUser(@ModelAttribute("user") User user, @RequestParam(value = "nameRoles", required = false) String[] roles) {
        userService.setRoleByUser(user, roles);
        userService.create(user);
        return new ModelAndView("redirect:/admin");
    }



    @DeleteMapping("/{id}")
    public ModelAndView deleteUser (@PathVariable("id") Long id){
        userService.delete(id);
        return new ModelAndView("redirect:/admin");
    }


    @GetMapping("/{id}")
    public ModelAndView getUserById (@PathVariable("id") Long id){
        ModelAndView modelAndView = new ModelAndView("show");
        modelAndView.addObject("user", userService.getUserById(id));
        return modelAndView;
    }


    @PatchMapping("/{id}")
    public ModelAndView updateUser(@PathVariable("id") Long id,
                                   @RequestParam(value = "nameRoles", required = false) String[] roles,
                                   @ModelAttribute User user) {
        user.setId(id);
        userService.setRoleByUser(user, roles);
        userService.create(user);
        return new ModelAndView("redirect:/admin");
    }



    @GetMapping("/{id}/edit")
    public ModelAndView  edit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("roles",roleService.getAllRoles());
        modelAndView.addObject("user", userService.getUserById(id));
        return modelAndView;
    }
}
