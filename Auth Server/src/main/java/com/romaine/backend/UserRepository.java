package com.romaine.backend;

import org.springframework.data.mongodb.repository.MongoRepository;



public interface UserRepository extends MongoRepository<User, String>{
	
	public User findByUsername(String username);
}
