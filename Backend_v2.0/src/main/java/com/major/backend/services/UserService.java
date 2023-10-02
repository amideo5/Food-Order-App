package com.major.backend.services;


import com.major.backend.exception.UserAlreadyExistException;
import com.major.backend.exception.UserNotExistException;
import com.major.backend.models.UserEntity;

import java.util.List;

public interface UserService{

	String createUser(UserEntity user) throws UserAlreadyExistException;
	UserEntity getUserByUserEmail(String email) throws UserNotExistException;
	String updateUser(String email, UserEntity user) throws UserNotExistException, UserAlreadyExistException;
	List<UserEntity> getUsers();
	String signInUser(UserEntity user);
	String getName(String email) throws UserNotExistException;
}
