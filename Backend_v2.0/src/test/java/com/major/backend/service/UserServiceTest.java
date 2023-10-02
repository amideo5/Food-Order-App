package com.major.backend.service;

import com.major.backend.exception.UserAlreadyExistException;
import com.major.backend.exception.UserNotExistException;
import com.major.backend.models.UserEntity;
import com.major.backend.repositories.UserRepository;
import com.major.backend.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    UserServiceImpl userService;



    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    }

    @Test
    public void testGetUsers() {
        UserEntity user1=new UserEntity();
        user1.setUserId("101");user1.setName("Aryan");user1.setEmail("aryantrvd@gmail.com");user1.setPassword("qwer1234");
        UserEntity user2=new UserEntity();
        user2.setUserId("102");user2.setName("Yogesh");user2.setEmail("yogesh@gmail.com");user2.setPassword("qwer1234");
        UserEntity user3=new UserEntity();
        user3.setUserId("103");user3.setName("Ishita");user3.setEmail("ishita@gmail.com");user3.setPassword("qwer1234");
        List<UserEntity> users = List.of(user1,user2,user3);
        UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
        when(userRepository.findAll()).thenReturn(users);

        List<UserEntity> actualUsers = userService.getUsers();

        assertEquals(users.size(), actualUsers.size());
        assertEquals(actualUsers, users);
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userRepository.findByEmail("abc@gmail.com")).thenReturn(null);
        UserEntity user = new UserEntity();
        user.setName("John Doe");user.setEmail("johndoe@example.com");user.setPassword("password123");
        UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
        String result = userService.createUser(user);

        assertEquals("User Created", result);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void testGetUserByEmail() throws UserNotExistException {
        UserEntity user = new UserEntity();
        user.setName("John Doe");user.setEmail("johndoe@example.com");user.setPassword("password123");
        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(user);
        UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
        UserEntity result = userService.getUserByUserEmail("johndoe@example.com");

        assertEquals("John Doe", result.getName());
        assertEquals("johndoe@example.com", result.getEmail());

        assertThrows(UserNotExistException.class, () -> userService.getUserByUserEmail("xyz@gmail.com"));
    }

    @Test
    public void testUpdateUserDetails() throws UserNotExistException, UserAlreadyExistException {
        UserEntity user = new UserEntity();
        user.setName("John Doe");user.setEmail("johndoe@example.com");user.setPassword("password123");
        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(user);

        UserEntity updatedUserEntity = new UserEntity();
        updatedUserEntity.setEmail("awe@gmail.com");
        updatedUserEntity.setName("Aryan");
        updatedUserEntity.setPassword("12345678");
        UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
        String result = userService.updateUser("johndoe@example.com", updatedUserEntity);

        assertEquals("User Updated", result);

        assertThrows(UserNotExistException.class, () -> userService.updateUser("qwe@gmail.com", updatedUserEntity));
    }

    @Test
    public void testSignInUserReturnsBadCredentialsIfMobileNoIsNull() {
        UserEntity user = new UserEntity();
        user.setPassword("password");
        UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
        String result = userService.signInUser(user);

        assertEquals("Bad credentials", result);
    }

    @Test
    public void testSignInUserReturnsBadCredentialsIfPasswordIsNull() {
        UserEntity user = new UserEntity();
        user.setEmail("xyz@gmail.com");
        UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
        String result = userService.signInUser(user);

        assertEquals("Bad credentials", result);
    }

    @Test
    public void testSignInUserReturnsSuccessfulIfUserExistsAndPasswordMatches() {
        UserEntity user = new UserEntity();
        user.setEmail("xyz@gmail.com");
        user.setPassword("password");
        UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
        UserEntity existingUser = new UserEntity();
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword("encoded_password");

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(existingUser);
        when(bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())).thenReturn(true);

        String result = userService.signInUser(user);

        assertEquals("Successful", result);
    }

    @Test
    public void testSignInUserReturnsBadCredentialsIfUserExistsAndPasswordDoesNotMatch() {
        UserEntity user = new UserEntity();
        user.setEmail("xyz@gmail.com");
        user.setPassword("password");
        UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
        UserEntity existingUser = new UserEntity();
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword("encoded_password");

        when(userRepository.findByEmail(existingUser.getEmail())).thenReturn(existingUser);
        when(bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())).thenReturn(false);

        String result = userService.signInUser(user);

        assertEquals("Bad Credentials", result);
    }

    @Test
    public void testGetFoodByName() throws UserNotExistException {
        UserEntity user = new UserEntity();
        user.setName("John Doe");user.setEmail("johndoe@example.com");user.setPassword("password123");
        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(user);
        UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
        String result = userService.getName("johndoe@example.com");

        assertEquals("John Doe", result);

        assertThrows(UserNotExistException.class, () -> userService.getName("abc@gmail.com"));
    }



}
