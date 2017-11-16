package com.kenshiro.instagram.repository;

import com.kenshiro.instagram.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

}
