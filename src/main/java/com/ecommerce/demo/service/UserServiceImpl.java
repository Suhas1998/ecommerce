package com.ecommerce.demo.service;

import com.ecommerce.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.ecommerce.demo.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        System.out.print("Hitting the Db to save the user: " + user);
        return userRepository.save(user);
    }

    public List<User> saveUsers(List<User> users) {
        System.out.print("Hitting the Db to save the users: " + users);
        return userRepository.saveAll(users);
    }

    public List<User> getAllUsers(){
        List<User> users= userRepository.findAll();
        System.out.print("Hitting the Db, number of users : " + users);
        return users;
    }


    public User getUserById(int id){
        System.out.print("Hitting the Db");
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByName(String name){
        System.out.print("Hitting the Db");
        return userRepository.findByName(name);
    }


    public String deleteUser(int id){
        System.out.print("Hitting the Db");
        userRepository.deleteById(id);
        return "User " + id + " removed";
    }


    public User updateUser(User user){
        System.out.print("Hitting the Db");
        User existingUser = userRepository.findById(user.getId()).orElse(null);
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());

        return userRepository.save(existingUser);
    }

    public boolean userExists(int id) {
        return userRepository.existsById(id);
    }
}
