package com.joy.api.controller;

import com.joy.api.data.model.User;
import com.joy.api.data.payloads.ResponseMessage;
import com.joy.api.exception.EmailNotUniqueException;
import com.joy.api.exception.UserNotFoundException;
import com.joy.api.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "Get the user information of this email.")
    @GetMapping("/{email}")
    public ResponseEntity<User> getUserByEmail( @PathVariable("email") String email) throws UserNotFoundException {
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok().body(user);
    }

    @ApiOperation("Update user information of this email.")
    @PutMapping("/{email}")
    public ResponseEntity<User> updateUserByEmail(@PathVariable("email") String email, @Valid @RequestBody User user) throws UserNotFoundException {
        User updatedUser = userService.updateUserByEmail(email, user);
        return ResponseEntity.ok().body(updatedUser);
    }

    @ApiOperation(value = "Add a new user to the database.")
    @PostMapping()
    public ResponseEntity<ResponseMessage> addUser(@Valid @RequestBody  User newUser) throws EmailNotUniqueException {
        userService.addUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Delete user of this email.")
    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable("email") String email) throws UserNotFoundException{
        userService.deleteUserByEmail(email);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}