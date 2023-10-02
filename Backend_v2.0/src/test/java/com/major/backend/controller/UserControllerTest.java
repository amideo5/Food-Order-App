package com.major.backend.controller;

import com.major.backend.controllers.UserController;
import com.major.backend.exception.UserAlreadyExistException;
import com.major.backend.exception.UserNotExistException;
import com.major.backend.models.UserEntity;
import com.major.backend.services.UserService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Test
    public void testGetUsers() {
        UserEntity user1=new UserEntity();
        user1.setUserId("101");user1.setName("Aryan");user1.setEmail("aryantrvd@gmail.com");user1.setPassword("qwer1234");
        UserEntity user2=new UserEntity();
        user2.setUserId("102");user2.setName("Yogesh");user2.setEmail("yogesh@gmail.com");user2.setPassword("qwer1234");
        UserEntity user3=new UserEntity();
        user3.setUserId("103");user3.setName("Ishita");user3.setEmail("ishita@gmail.com");user3.setPassword("qwer1234");
        List<UserEntity> users = List.of(user1,user2,user3);
        when(userService.getUsers()).thenReturn(users);
        ResponseEntity<?> response = userController.getUsers();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).getUsers();
    }

    @Test
    public void testGetUser_Success() throws UserNotExistException {
        String emailId = "test@example.com";
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(emailId);
        when(userService.getUserByUserEmail(emailId)).thenReturn(userEntity);
        ResponseEntity<?> responseEntity = userController.getUser(emailId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userEntity, responseEntity.getBody());
    }

    @Test
    public void testGetUser_UserNotExistException() throws UserNotExistException {
        String emailId = "test@example.com";
        String errorMessage = "User does not exist";
        when(userService.getUserByUserEmail(emailId)).thenThrow(new UserNotExistException(errorMessage));
        ResponseEntity<?> responseEntity = userController.getUser(emailId);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(errorMessage, responseEntity.getBody());
    }

    @Test
    public void testCreateUser_Success() throws UserAlreadyExistException {
        UserEntity userDetails = new UserEntity();
        userDetails.setName("testuser");
        userDetails.setPassword("testpassword");

        when(userService.createUser(userDetails)).thenReturn("User created successfully");

        ResponseEntity<?> response = userController.createUser(userDetails);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User created successfully", response.getBody());
    }

    @Test
    public void testCreateUser_UserAlreadyExistException() throws UserAlreadyExistException {
        UserEntity userDetails = new UserEntity();
        userDetails.setName("testuser");
        userDetails.setPassword("testpassword");

        when(userService.createUser(userDetails)).thenThrow(new UserAlreadyExistException("User already exists"));

        ResponseEntity<?> response = userController.createUser(userDetails);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws Exception {
        String emailId = "test@example.com";
        UserEntity userDetails = new UserEntity();
        userDetails.setName("Test");
        userDetails.setEmail("User");
        userDetails.setPassword("password");
        when(userService.updateUser(eq(emailId), any(UserEntity.class))).thenReturn("User updated successfully.");
        ResponseEntity<?> response = userController.updateUser(emailId, userDetails);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully.", response.getBody());
    }

    @Test
    public void testAuthenticateUserSuccess() {
        UserEntity user = new UserEntity();
        user.setName("testuser");
        user.setPassword("testpassword");

        when(userService.signInUser(any(UserEntity.class))).thenReturn("authentication successful");

        ResponseEntity<?> responseEntity = userController.authenticateUser(user);

        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().equals("authentication successful");
    }

    @Test
    public void testGetAccountName() throws UserNotExistException {
        String email = "test@example.com";
        String expectedName = "John Doe";

        when(userService.getName(email)).thenReturn(expectedName);

        ResponseEntity<?> response = userController.getAccountName(email);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedName, response.getBody());
    }

    @Test
    public void testGetAccountName_UserNotExistException() throws UserNotExistException {
        String email = "test@example.com";

        when(userService.getName(email)).thenThrow(new UserNotExistException("User not found"));

        ResponseEntity<?> response = userController.getAccountName(email);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User does not exist", response.getBody());
    }


}
