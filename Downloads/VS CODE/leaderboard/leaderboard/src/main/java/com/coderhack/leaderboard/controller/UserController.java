package com.coderhack.leaderboard.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhack.leaderboard.dtos.UserRegistrationRequestDTO;
import com.coderhack.leaderboard.dtos.UserResponseDTO;
import com.coderhack.leaderboard.dtos.UserScoreUpdateRequestDTO;
import com.coderhack.leaderboard.exception.UserAlreadyExistsException;
import com.coderhack.leaderboard.service.UserService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/users")


public class UserController {

    private final UserService userService;

    // Constructor injection
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //GetMapping -> Retrieve all users
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO>users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    //getmapping with a aspecific id 
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO>getUserById(@PathVariable String userId){
        UserResponseDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    
        
    

    // POST /users - Register a new user
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRegistrationRequestDTO requestDto) throws UserAlreadyExistsException {
        UserResponseDTO response = userService.registerUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }


    @PutMapping("/{userId}") 
    public ResponseEntity<Void> updateUserScore(@PathVariable String userId, @Valid @RequestBody UserScoreUpdateRequestDTO request) {
        userService.updateUserScore(userId, request);
        return ResponseEntity.noContent().build();
    }


    
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
    }

    
    
}
