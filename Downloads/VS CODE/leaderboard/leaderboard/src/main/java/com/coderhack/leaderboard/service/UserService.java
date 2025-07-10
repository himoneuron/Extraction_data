package com.coderhack.leaderboard.service;

import java.util.List;

import com.coderhack.leaderboard.dtos.UserRegistrationRequestDTO;
import com.coderhack.leaderboard.dtos.UserResponseDTO;
import com.coderhack.leaderboard.dtos.UserScoreUpdateRequestDTO;
import com.coderhack.leaderboard.exception.UserNotFoundException;

public interface  UserService 
{
    UserResponseDTO registerUser( UserRegistrationRequestDTO request); // This class will contain methods for user registration, score updates, and leaderboard retrieval.
    List<UserResponseDTO>getAllUsers(); // This method will return a list of all registered users.
    UserResponseDTO getUserById(String userId) throws UserNotFoundException; // This method will retrieve a user by their ID.
    // It will interact with the UserRepository to perform CRUD operations on User entities.
    UserResponseDTO updateUserScore(String userId, UserScoreUpdateRequestDTO request);
    // Example method signatures:
    // public UserResponseDTO registerUser(UserRegistrationRequestDTO request);
    // public UserResponseDTO updateUserScore(String userId, UserScoreUpdateRequestDTO
    void deleteUser(String userId);

    
}
