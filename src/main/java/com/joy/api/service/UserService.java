package com.joy.api.service;

import com.joy.api.data.model.User;
import com.joy.api.data.payloads.ResponseMessage;
import com.joy.api.data.repository.UserRepository;
import com.joy.api.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
    public Optional<User> updateUserByEmail(String email, User user) throws UserNotFoundException {
        Optional<User> current_user = userRepository.findById(email);
        if (current_user.isEmpty()){
            throw new UserNotFoundException(email);
        }
        else{
            current_user.get().setFirstName(user.getFirstName());
            current_user.get().setLastName(user.getLastName());
            current_user.get().setPassword(user.getPassword());
            userRepository.save(current_user.get());
            return current_user;
        }
    }

    @Override
    public ResponseMessage addUser(User user) {
        User newUser;
        newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        return new ResponseMessage("New User created successfully");
    }
}
