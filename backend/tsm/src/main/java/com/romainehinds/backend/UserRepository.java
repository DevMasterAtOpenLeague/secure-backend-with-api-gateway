package com.romainehinds.backend;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.romainehinds.backend.User;

public interface UserRepository extends MongoRepository<User, String>{
	
	public User findByUsername(String username);
}
