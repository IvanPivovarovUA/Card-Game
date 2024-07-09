package com.petproject.cardgame.repository;

import com.petproject.cardgame.entity.GameTableEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameTableRepository extends MongoRepository<GameTableEntity, String> {

    Optional<GameTableEntity> findById(String Id);
}
