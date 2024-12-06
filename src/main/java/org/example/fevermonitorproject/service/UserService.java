package org.example.fevermonitorproject.service;

import org.example.fevermonitorproject.model.User;
import org.example.fevermonitorproject.model.UserLoginRequest;
import org.example.fevermonitorproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public User getAuthenticatedUser() {
        // Fetch the username of the currently logged-in user
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        // Fetch the User object from the database
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }

    public Long login (UserLoginRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(request.getPassword())) {
                return user.getId();
            }
        }
        return null;
    }


    public boolean register(User user) {
        // Check if the username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            System.out.println("Username is already taken. Please try again!");
            return false; // Registration failed
        }
        // Save the new user to the database
        userRepository.save(user);
        System.out.println("User registered successfully: " + user.getUsername());
        return true; // Registration successful
    }

    public String getUserByName(String username) {
        return "Tere, " + username;

    }

    public String addUser(User user) {
       userRepository.save(user);
        // users.add(user);
        printUsers();
        System.out.println("User with ID " + user.getUsername() + " has been added");
        return "User " + user.getUsername() + " has been added successfully";
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        return userRepository.findAll();
        // TODO !
        // Pane sinna userrepository
    }

    //public String updateUser(Long id, User user) {
    //    userRepository.save(user);
    //    for (User existingUser : getAllUsers()) {
    //        if (existingUser.getId().equals(id)) {
    //            existingUser.setFirstName(user.username);
    //            existingUser.setLastName(user.username);
    //            existingUser.setEmail(user.getEmail());
    //            System.out.println("E-mail updated with: " + existingUser.getEmail());
    //            return "User with ID " + id + " has been updated.";
    //        }
//
    //    }
    //    return "User with ID " + id + " was not found";
    //}

    public String deleteUserById(Long userId) {
        userRepository.deleteById(userId);
        for (User user : getAllUsers())
            if (user.getId().equals(userId)) {
                getAllUsers().remove(userId);
                System.out.println("User with ID " + userId + " deleted.");
            }
        System.out.println("New user list is:" + getAllUsers());
        return "User with ID " + userId + " was not found";
    }

    public User getUserById(Long userId) {
        Optional<User> user =  userRepository.findById(userId);
        return user.orElse(null);
    }

    public String getFullName(String firstName, String lastName) {
        return "Full name is: " + firstName + " " + lastName;
    }

    public void printUsers() {
        System.out.println(getAllUsers());
    }
}