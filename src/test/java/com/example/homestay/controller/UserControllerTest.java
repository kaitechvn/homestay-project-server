//package com.example.homestay.controller;
//
//import com.example.homestay.config.TestConfig;
//import com.example.homestay.controller.UserController;
//import com.example.homestay.dto.reponse.UserResponse;
//import com.example.homestay.dto.request.UserRequest;
//import com.example.homestay.exception.ExistingException;
//import com.example.homestay.service.user.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import java.time.LocalDate;
//
//import static com.example.homestay.exception.ErrorCode.USER_EXISTED;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@WebMvcTest(UserController.class)
//@ContextConfiguration(classes = TestConfig.class)
//public class UserControllerTest {
//
//    @Autowired
//    private UserController userController;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private UserService userService;
//
//    private UserRequest mockUserRequest;
//    private UserResponse userResponse;
//
//    @BeforeEach
//    public void init() {
//        mockUserRequest = UserRequest.builder()
//                .firstName("John")
//                .lastName("Doe")
//                .username("johndoe")
//                .password("StrongP@ssword123")
//                .email("johndoe@example.com")
//                .phoneNumber("0936782589")
//                .dOb(LocalDate.of(2000, 1, 1))
//                .build();
//
//
//        userResponse = new UserResponse();
//        userResponse.setFirstName("testuser");
//        userResponse.setEmail("testuser@example.com");
//    }
//
//
//    private static final String END_POINT_PATH = "/users";
//
//    @Test
//    public void testCreateUserSuccess() throws Exception {
//
//        mockMvc.perform(
//                        post(END_POINT_PATH)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(mockUserRequest)))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//    @Test
//    public void testCreateUserInvalidEmail() throws Exception {
//        // Set invalid email in request
//        mockUserRequest.setEmail("invalid-email");
//
//        mockMvc.perform(
//                        post(END_POINT_PATH)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(objectMapper.writeValueAsString(mockUserRequest)))
//                .andExpect(status().isBadRequest()) // Expecting 400 Bad Request
//                .andDo(print());
//    }
//
//    @Test
//    public void testCreateUserExistedId() throws Exception {
//
//        Mockito.when(userService.create(mockUserRequest)).thenThrow(new ExistingException(USER_EXISTED));
//
//        mockMvc.perform(
//                post(END_POINT_PATH)
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(mockUserRequest)))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }
//
//    @Test
//    public void testGetUserSuccess() throws Exception {
//        Integer userId = 1;
//        Mockito.when(userService.get(userId)).thenReturn(userResponse);
//
//        mockMvc.perform(
//                        get(END_POINT_PATH + "/" + userId)
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk()) // Expecting 200 OK
//                .andDo(print());
//    }
//
//    @Test
//    public void testDeleteUserSuccess() throws Exception {
//        Integer userId = 1;
//
//        mockMvc.perform(
//                        delete(END_POINT_PATH + "/" + userId)
//                                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
//
//
//
//
//
//
//}
