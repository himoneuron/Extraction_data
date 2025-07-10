package com.coderhack.leaderboard.dtos;

import java.util.Set;

import com.coderhack.leaderboard.model.Badge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor 
@AllArgsConstructor 
@Data
@Builder
public class UserResponseDTO {
    private String userId;
    private String username;
    private int score;
    private Set<Badge> badges;
}