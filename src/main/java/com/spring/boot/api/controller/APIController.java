package com.spring.boot.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.api.model.User;
import com.spring.boot.api.repository.UserRepository;

/**
 * Class to define the request mapping to serve the
 * user requests (GET & POST)
 *
 * @author Vishwajit
 */

@RestController
@RequestMapping("/api/user")
public class APIController {

    @Autowired
    private UserRepository userRepository;

    /**
     * Method to fetch all the user data present in DB
     *
     * @return List of users
     */
    @GetMapping
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Method to fetch the user data from DB based in user ID
     *
     * @param id User ID
     * @return User data
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable(value = "id") long id) {

        Optional<User> user = userRepository.findById(id);

        return user.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Method to create a user in DB
     *
     * @param user User data in payload
     */
    @PostMapping
    public User saveUser(@Validated @RequestBody User user) {
        return userRepository.save(user);
    }
}