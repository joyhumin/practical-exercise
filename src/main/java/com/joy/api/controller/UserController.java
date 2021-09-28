package com.joy.api.controller;

import com.joy.api.data.model.User;
import com.joy.api.data.payloads.ResponseMessage;
import com.joy.api.exception.UserNotFoundException;
import com.joy.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail( @PathVariable("email") String email) throws UserNotFoundException {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/{email}")
    public ResponseEntity<User> updateUserByEmail(@PathVariable("email") String email, @RequestBody User user) throws UserNotFoundException {
        Optional<User> updatedUser = userService.updateUserByEmail(email, user);
        return ResponseEntity.ok().body(updatedUser.get());
    }

    @PostMapping()
    public ResponseEntity<ResponseMessage> addUser(@RequestBody  User newUser){
        userService.addUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable("email") String email) throws UserNotFoundException{
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}