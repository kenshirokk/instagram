package com.kenshiro.instagram.repository;

import com.kenshiro.instagram.document.Node;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends MongoRepository<Node, String> {
    Node findByNodeId(String nodeId);
    List<Node> findByDownloadedExistsOrDownloadedIsFalse(boolean exists);
}
