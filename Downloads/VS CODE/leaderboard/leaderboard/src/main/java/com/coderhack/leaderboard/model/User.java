package com.coderhack.leaderboard.model;

import java.util.EnumSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Add import for Badge if it exists in the same package or adjust the package accordingly



@Builder
@Document(collection = "users")
@Data
@NoArgsConstructor  
@AllArgsConstructor

public class User 
{
    @Id
    @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Min(value = 0, message = "Score must be at least 0")
    @Max(value = 100, message = "Score cannot exceed 100")
    private int score;

    @Builder.Default
    private Set<Badge> badges = EnumSet.noneOf(Badge.class);
    

}
