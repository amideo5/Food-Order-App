package com.major.backend.controllers;


import com.major.backend.exception.UserAlreadyExistException;
import com.major.backend.exception.UserNotExistException;
import com.major.backend.models.UserEntity;
import com.major.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:3000/")
public class UserController {

	@Autowired
	UserService userService;


	@GetMapping(path = "/getUsers")
	public ResponseEntity<?> getUsers(){
		List<UserEntity> users = userService.getUsers();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@GetMapping(path = "/getUser/{emailId}")
	public ResponseEntity<?> getUser(@PathVariable String emailId) throws UserNotExistException {
		try{
			UserEntity user = userService.getUserByUserEmail(emailId);
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		catch (UserNotExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping(path = "/createUser")
	public ResponseEntity<?> createUser(@RequestBody UserEntity userDetails) throws UserAlreadyExistException {
		try {
			String createUser = userService.createUser(userDetails);
			return ResponseEntity.status(HttpStatus.OK).body(createUser);
		}catch (UserAlreadyExistException e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping(path = "/updateUser/{emailId}")
	public ResponseEntity<?> updateUser(@PathVariable String emailId, @RequestBody UserEntity userDetails) throws UserNotExistException, UserAlreadyExistException {
		try {
			String updateUser = userService.updateUser(emailId, userDetails);
			return ResponseEntity.status(HttpStatus.OK).body(updateUser);
		}
		catch (UserNotExistException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody UserEntity user){
		try {
			String signin = userService.signInUser(user);
			return ResponseEntity.status(HttpStatus.OK).body(signin);
		}
		catch(Exception e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/name/{email}")
	public ResponseEntity<?> getAccountName(@PathVariable String email) throws UserNotExistException{
		try {
			String name = userService.getName(email);
			return ResponseEntity.status(HttpStatus.OK).body(name);
		} catch (UserNotExistException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}


	
}
