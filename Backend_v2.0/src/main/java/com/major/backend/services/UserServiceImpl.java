package com.major.backend.services;

import com.major.backend.exception.UserAlreadyExistException;
import com.major.backend.exception.UserNotExistException;
import com.major.backend.models.UserEntity;
import com.major.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.random.RandomGenerator;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public String createUser(UserEntity user) throws UserAlreadyExistException {
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new UserAlreadyExistException(user.getEmail());
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("#" + RandomGenerator.getDefault().nextInt(999999999));
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
        return "User Created";
    }

    @Override
    public UserEntity getUserByUserEmail(String email) throws UserNotExistException {
        if (userRepository.findByEmail(email) == null) {
            throw new UserNotExistException(email);
        }
        return userRepository.findByEmail(email);
    }

    @Override
    public String updateUser(String email, UserEntity user) throws UserNotExistException, UserAlreadyExistException{
        if (userRepository.findByEmail(email) == null) {
            throw new UserNotExistException(email);
        }
        UserEntity userEntity = userRepository.findByEmail(email);
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
        return "User Updated";
    }

    @Override
    public String signInUser(UserEntity user) {
        if(user.getEmail()==null||user.getPassword()==null)
            return "Bad credentials";
        UserEntity user1 = userRepository.findByEmail(user.getEmail());
        if(bCryptPasswordEncoder.matches(user.getPassword() ,user1.getPassword()))
            return "Successful";
        else
            return "Bad Credentials";
    }

    @Override
    public String getName(String email) throws UserNotExistException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null){
            throw new UserNotExistException(email);
        }
        return userEntity.getName();
    }

}
