package com.kenshiro.instagram.repository;

import com.kenshiro.instagram.document.Node;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NodeRepository extends MongoRepository<Node, String> {
}
