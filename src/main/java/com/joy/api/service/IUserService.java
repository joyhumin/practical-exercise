package com.joy.api.service;

import com.joy.api.data.model.User;
import com.joy.api.data.payloads.ResponseMessage;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Optional;

@Component
public interface IUserService {
    User getUserByEmail(String email);
    void deleteUserByEmail(String email);
    User updateUserByEmail(String email,User user);
    ResponseMessage addUser(User user);
}
