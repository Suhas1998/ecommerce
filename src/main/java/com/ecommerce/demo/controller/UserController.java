package com.ecommerce.demo.controller;


import com.ecommerce.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ecommerce.demo.service.UserServiceImpl;

import java.util.Collections;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/")
    public String viewHomePage(Model model){
        model.addAttribute("listUsers", userService.getAllUsers() );
//        System.out.print("Get Req");
        return "index";
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/user/id/{id}")
    @Cacheable(value = "users", key = "#id")
    public User getUserById(@PathVariable int id){
        return userService.getUserById(id);
    }

    @GetMapping("/user/name/{name}")
    public User getUserByName(@PathVariable String name){
        return userService.getUserByName(name);
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @PostMapping("/addUsers")
    public List<User> addUsers(@RequestBody List<User> users){
        return userService.saveUsers(users);
    }

    @PutMapping("/update")
    @CachePut(value = "users", key = "#user.getId()")
    public User updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    @CacheEvict(value = "users", allEntries = true)
    public String deleteUser(@PathVariable int id) throws Exception {
        boolean userExists = userService.userExists(id);
        if(userExists){
            return userService.deleteUser(id);
        }else{
            throw new Exception("User not found !");
        }
    }
}
