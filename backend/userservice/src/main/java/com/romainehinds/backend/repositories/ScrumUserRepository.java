package com.romainehinds.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.romainehinds.backend.models.ScrumUser;

@Repository
public interface ScrumUserRepository extends MongoRepository<ScrumUser, String>{

	public ScrumUser findByUsername(String username);
}
