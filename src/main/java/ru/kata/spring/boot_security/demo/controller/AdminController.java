package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;



    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public String showUserList(Model model, Authentication authentication,
                               @RequestParam(required = false) Boolean showModaltip,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String message) {
        User user = new User();
        String email = authentication.getName();
        User editUser = userService.findUserByEmail(email);
        String roleString = editUser.getRoles().stream().map(role -> role.getName().replaceAll("ROLE_", ""))
                .collect(Collectors.joining(" "));
        model.addAttribute("user", user);
        model.addAttribute("edit_user", editUser);
        model.addAttribute("edit_roles", roleString);
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("showModaltip", showModaltip);
        model.addAttribute("name", name);
        model.addAttribute("message", message);
        return "list-users";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") @Valid User user) {
        userService.add(user);
        return "redirect:/admin/list";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/list";
    }
    @PostMapping("/resetShowModaltip")
    public String resetShowModaltip (Model model){
        model.addAttribute("showModaltip", false);
        return "redirect:/admin/list";
    }
}
