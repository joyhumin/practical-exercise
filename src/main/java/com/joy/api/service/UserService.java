package com.joy.api.service;

import com.joy.api.data.model.User;
import com.joy.api.data.payloads.ResponseMessage;
import com.joy.api.data.repository.UserRepository;
import com.joy.api.exception.EmailNotUniqueException;
import com.joy.api.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findById(email)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Override
    public void deleteUserByEmail(String email) throws UserNotFoundException{
        if (userRepository.existsById(email)){
            userRepository.deleteById(email);
        } else throw new UserNotFoundException(email);

    }

    @Override
    public User updateUserByEmail(String email, User user) throws UserNotFoundException {
        User current_user = userRepository.findById(email).orElseThrow(() -> new UserNotFoundException(email));
            current_user.setFirstName(user.getFirstName());
            current_user.setLastName(user.getLastName());
            current_user.setPassword(user.getPassword());
            userRepository.save(current_user);
            return current_user;

    }

    @Override
    public ResponseMessage addUser(User user) throws EmailNotUniqueException{
        if(userRepository.existsById(user.getEmail())){
            throw new EmailNotUniqueException(user.getEmail());
        }
        User newUser;
        newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        userRepository.save(newUser);

        return new ResponseMessage("New User created successfully");
    }
}
