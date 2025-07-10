package com.coderhack.leaderboard;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.coderhack.leaderboard.dtos.UserResponseDTO;
import com.coderhack.leaderboard.exception.UserNotFoundException;
import com.coderhack.leaderboard.model.User;
import com.coderhack.leaderboard.repository.UserRepository;
import com.coderhack.leaderboard.serviceImpl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest 
{
    // The class we are testing
    @InjectMocks
    private UserServiceImpl userService;

    // Mocking the dependencies of UserServiceImpl
    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    private User user1;
    private User user2;
    private UserResponseDTO userResponseDTO1;


    @BeforeEach
    void setUp() {
        // Setup mock data that can be used in multiple tests
        user1 = new User("u1", "Alex", 10, null);
        user2 = new User("u2", "Maria", 20, null);
        userResponseDTO1 = new UserResponseDTO("u1", "Alex", 10, null);
    }

    // =================================================================
    // Tests for getAllUsers()
    // =================================================================

    @Test
    void testGetAllUsers_Success() {
        // 1. Arrange: Define the behavior of the mocks
        List<User> userList = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(userList);

        // Mock the mapping for each user
        when(modelMapper.map(user1, UserResponseDTO.class)).thenReturn(userResponseDTO1);
        when(modelMapper.map(user2, UserResponseDTO.class)).thenReturn(new UserResponseDTO("u2", "Maria", 20, null));

        // 2. Act: Call the method being tested
        List<UserResponseDTO> result = userService.getAllUsers();

        // 3. Assert: Verify the results
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alex", result.get(0).getUsername());
        assertEquals("Maria", result.get(1).getUsername());
    }

    @Test
    void testGetAllUsers_ReturnsEmptyList() {
        // 1. Arrange: Mock the repository to return an empty list
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        // 2. Act: Call the method
        List<UserResponseDTO> result = userService.getAllUsers();

        // 3. Assert: Verify the list is empty
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    // =================================================================
    // Tests for getUserById()
    // =================================================================

    @Test
    void testGetUserById_Success() {
        // 1. Arrange: Mock the repository to return an Optional containing our user
        when(userRepository.findById("u1")).thenReturn(Optional.of(user1));
        when(modelMapper.map(user1, UserResponseDTO.class)).thenReturn(userResponseDTO1);

        // 2. Act: Call the method
        UserResponseDTO result = userService.getUserById("u1");

        // 3. Assert: Verify the correct user DTO is returned
        assertNotNull(result);
        assertEquals("u1", result.getUserId());
        assertEquals("Alex", result.getUsername());
    }

    @Test
    void testGetUserById_ThrowsUserNotFoundException() {
        // 1. Arrange: Mock the repository to return an empty Optional
        String nonExistentId = "u999";
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // 2. Act & 3. Assert: Verify that the correct exception is thrown
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(nonExistentId);
        });
    }
}


