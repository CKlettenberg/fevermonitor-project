package org.example.fevermonitorproject.controller;

import org.example.fevermonitorproject.model.User;
import org.example.fevermonitorproject.repository.UserRepository;
import org.example.fevermonitorproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/user/hello")
    public String getHello() {
        return "Hello, user!";
    }

    @GetMapping("/multiple-users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") Long userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/{name}")
    public String getUserByName(@PathVariable("name") String userName) {
        return userService.getUserByName(userName);
    }

    @GetMapping("/fullname")
    public String getFullName(@RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName) {
        return userService.getFullName(firstName, lastName);
    }

    @GetMapping("/print-users")
    private void users() {
        userService.printUsers();
    }

    @DeleteMapping("/delete-user/{id}")
    public String deleteUserById(@PathVariable("id") Long userId) {
        return userService.deleteUserById(userId);
    }

    @PostMapping("/add-user")
    public String addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    //@PutMapping("/update-user-details/{id}")
    //public String updateUser(@PathVariable("id") Long id, @RequestBody User user) {
    //    return userService.updateUser(id, user);
    //}

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.register(user)) {
            return ResponseEntity.ok("User registered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists.");
        }
    }
}