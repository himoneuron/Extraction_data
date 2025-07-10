package com.coderhack.leaderboard.serviceImpl;

import java.util.HashSet;
//import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhack.leaderboard.dtos.UserRegistrationRequestDTO;
import com.coderhack.leaderboard.dtos.UserResponseDTO;
import com.coderhack.leaderboard.dtos.UserScoreUpdateRequestDTO;
import com.coderhack.leaderboard.exception.UserAlreadyExistsException;
import com.coderhack.leaderboard.exception.UserNotFoundException;
import com.coderhack.leaderboard.model.Badge;
import com.coderhack.leaderboard.model.User;
import com.coderhack.leaderboard.repository.UserRepository;
import com.coderhack.leaderboard.service.UserService;


@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository ;
    private final ModelMapper modelMapper;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserResponseDTO registerUser(UserRegistrationRequestDTO request) {
        // TODO Auto-generated method stub
        if (userRepository.existsById(request.getUserId())){
        throw new UserAlreadyExistsException("User with ID '" + request.getUserId() + "' already exists.");
        }
        // Convert DTO to entity
        User user = modelMapper.map(request,User.class);
        user.setScore(0); // Initialize score to 0
        //user.setBadges(EnumSet.noneOf(Badge.class)); // Initialize badges to empty set
        User savedUser = userRepository.save(user);
        // Convert entity back to DTO
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
       List<User>users= userRepository.findAll();
       return users.stream().map(user -> modelMapper
       .map(user, UserResponseDTO.class))
       .collect(Collectors.toList());
       // throw new UnsupportedOperationException("Unimplemented method 'getAllUsers'");
    }

    public UserResponseDTO getUserById(String userId) {

        // Check if user exists

        // TODO Auto-generated method stub
       User user = userRepository.findById(userId)
       .orElseThrow(() -> new UserNotFoundException("User with ID '" + userId + "' not found."));
        // Convert entity to DTO
        return modelMapper.map(user, UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO updateUserScore(String userId , UserScoreUpdateRequestDTO request) {
        // TODO Auto-generated method stub
        User user = userRepository.findById(userId).
                orElseThrow(() 
                -> new UserNotFoundException("User with ID '" + userId + "' not found."));     
        // Update the user's score
        user.setScore(request.getScore());
        // Save the updated user entity back to the repository
         // *** BADGE ALLOCATION LOGIC ***
    
    
        Set<Badge> badges = new HashSet<>();
        int score = user.getScore();

        if (score >= 60) {
        badges.add(Badge.CODE_MASTER);
        }
        if (score >= 30) {
        badges.add(Badge.CODE_CHAMP);
        }
        if (score >= 1) {
        badges.add(Badge.CODE_NINJA);
        }

        user.setBadges(badges);
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, UserResponseDTO.class);
    }

   @Override
    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with ID '" + userId + "' not found.");
        }
        userRepository.deleteById(userId);
    }

    
}
