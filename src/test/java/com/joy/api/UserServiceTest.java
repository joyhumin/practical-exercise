package com.joy.api;

import com.joy.api.data.model.User;
import com.joy.api.data.payloads.ResponseMessage;
import com.joy.api.data.repository.UserRepository;
import com.joy.api.exception.UserNotFoundException;
import com.joy.api.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setupService(){
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testGetUserByEmailSuccess() throws UserNotFoundException {
        String email = "abc@gmail.com";
        User user = new User(email, "joy", "hu", "abcde");

        when(userRepository.findById(email)).thenReturn(Optional.of(user));

        // test
        Optional<User> testUser = userRepository.findById(email);
        assertEquals("email", testUser.get().getEmail());
        assertEquals("joy", testUser.get().getFirstName());

    }

    @Test
    void TestGetUserThrowUserNotFound() throws UserNotFoundException {
        String email = "abc@gmail.com";

        doThrow(new UserNotFoundException(email))
                .when(userService)
                .getUserByEmail(email);

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(email));

        Assertions.assertSame("Cannot find the user with email "+email, ex.getMessage());
    }

    @Test
    void testDeleteUserByEmailSuccess() throws UserNotFoundException{
        String email = "abc@gmail.com";

        doNothing().when(userService).deleteUserByEmail(email);

        verify(userService, times(1)).deleteUserByEmail(email);

    }

    @Test
    void testDeleteUserThrowException() throws UserNotFoundException{
        String email = "hello@outlook.com";
        doThrow(new UserNotFoundException(email))
                .when(userService)
                .deleteUserByEmail(email);

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> userService.deleteUserByEmail(email));

        Assertions.assertSame("Cannot find the user with email "+email, ex.getMessage());
    }

    @Test
    void testAddUser() throws Exception{
        String email = "abc@gmail.com";
        User user = new User(email, "joy", "hu", "abcde");

        when(userService.addUser(user)).thenReturn(new ResponseMessage("New User created successfully"));

        verify(userService, times(1)).addUser(user);
    }

    @Test
    void testUpdateUserSuccess() throws UserNotFoundException {
        String email = "exinalg@outlook.com";
        User user = new User(email, "joy", "hu", "abcde");

        when(userService.updateUserByEmail(email,user)).thenReturn(user);

        Optional<User> expected_user = userRepository.findById(email);

        Assertions.assertEquals(expected_user.get(), user);

    }

    @Test
    void testUpdateUserThrowUserNotFound() throws UserNotFoundException {
        String email = "hello@outlook.com";
        User user = new User(email, "joy", "hu", "abcde");
        doThrow(new UserNotFoundException(email))
                .when(userService)
                .updateUserByEmail(email, user);

        UserNotFoundException ex = assertThrows(UserNotFoundException.class, () -> userService.updateUserByEmail(email, user));

        Assertions.assertSame("Cannot find the user with email "+email, ex.getMessage());
    }
}

