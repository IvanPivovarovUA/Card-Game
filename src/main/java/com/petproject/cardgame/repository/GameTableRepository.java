package com.petproject.cardgame.repository;

import com.petproject.cardgame.data.document.GameTableDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameTableRepository extends MongoRepository<GameTableDocument, String> {

    @Override
    Optional<GameTableDocument> findById(String Id);



}
