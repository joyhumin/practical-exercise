package com.joy.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.joy.api.controller.UserController;
import com.joy.api.data.model.User;
import com.joy.api.data.payloads.ResponseMessage;
import com.joy.api.data.repository.UserRepository;
import com.joy.api.exception.UserNotFoundException;
import com.joy.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.ValidationException;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @MockBean
    UserService userService;
    User USER1 = new User("abc@gmail.com", "Joy", "Hu", "2890nlgnl");

    @Test
    void testGetUserSuccess() throws Exception{
        User user = USER1;
        Mockito.when(userService.getUserByEmail(USER1.getEmail())).thenReturn(USER1);
        String uri = "/users/"+ user.getEmail();

        mockMvc.perform(MockMvcRequestBuilders
                .get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName", is("Hu")))
                .andExpect(jsonPath("$.firstName", is("Joy")))
                .andExpect(jsonPath("$.email", is("abc@gmail.com")))
                .andExpect(jsonPath("$.password", is("2890nlgnl")));
    }

    @Test
    void testGetUserThrowUserNotFound() throws Exception{
        Mockito.when(userService.getUserByEmail("mock@email.com")).thenThrow(new UserNotFoundException("mock@email.com"));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/mock@email.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is("404 NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("Cannot find the user with email mock@email.com")));
    }

    @Test
    void testAddUserSuccess() throws Exception {
        User newUser = User.builder()
                .firstName("Anna")
                .lastName("Wat")
                .email("an.w@gmail.com")
                .password("password")
                .build();

        Mockito.when(userService.addUser(newUser)).thenReturn(new ResponseMessage("New User created successfully"));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(newUser));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateUserSuccess() throws Exception{
        User updatedUser = User.builder()
                .firstName("Joy")
                .lastName("Hu")
                .email("abc@gmail.com")
                .password("password")
                .build();

        Mockito.when(userService.updateUserByEmail(USER1.getEmail(),updatedUser)).thenReturn(updatedUser);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/users/"+USER1.getEmail())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedUser));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password", is("password")))
                .andExpect(jsonPath("$.email", is("abc@gmail.com")));
    }

    @Test
    void testUpdateUserThrowNotFound() throws Exception {
        User updatedUser = User.builder()
                .firstName("Ana")
                .lastName("Lexi")
                .email("wrong@email.com")
                .password("password")
                .build();
        String randomEmail = "abc@email.com";

        Mockito.when(userService.updateUserByEmail(randomEmail, updatedUser)).thenThrow( new UserNotFoundException(randomEmail));

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/users/"+randomEmail)
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedUser));

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is("404 NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("Cannot find the user with email "+randomEmail)));
    }

    @Test
    void testDeleteUserSuccess() throws Exception{
        String email = "abc@gmail.com";

        doNothing().when(userService).deleteUserByEmail(email);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/users/"+email)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest).andExpect(status().isNoContent());
    }

    @Test
    void testDeleteUserThrowNotFound() throws Exception{
        String email = "wrong@gmail.com";
        doThrow(new UserNotFoundException(email)).when(userService).deleteUserByEmail(email);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/users/"+email)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code", is("404 NOT_FOUND")))
                .andExpect(jsonPath("$.message", is("Cannot find the user with email "+email)));

    }

    @Test
    void testBadRequst() throws Exception{
        String email = "123";

        MockHttpServletRequestBuilder mockRequest =
                MockMvcRequestBuilders.delete("/users/" + email)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest());
    }
}
