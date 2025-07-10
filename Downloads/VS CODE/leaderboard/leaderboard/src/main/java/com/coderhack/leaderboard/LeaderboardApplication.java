package com.coderhack.leaderboard;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LeaderboardApplication {

	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
	public static void main(String[] args) {
		SpringApplication.run(LeaderboardApplication.class, args);
	}

}
