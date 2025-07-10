package com.coderhack.leaderboard.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.coderhack.leaderboard.model.User;

public interface UserRepository extends MongoRepository<User, String>{
/*
save(User entity): Saves a new user or updates an existing one.
saveAll(Iterable<User> entities): Saves or updates multiple users at once.
findById(String id): Retrieves a single user by their ID. It returns an Optional<User>.
findAll(): Retrieves all users in the collection.
findAllById(Iterable<String> ids): Retrieves multiple users by their IDs.
count(): Returns the total number of users in the collection.
existsById(String id): Checks if a user with a given ID exists. Returns true or false.
deleteById(String id): Deletes a user by their ID.
delete(User entity): Deletes a user based on the provided entity object.
deleteAll(): Deletes all users in the collection.
deleteAll(Iterable<User> entities): Deletes a specific list of users.
 */
}
