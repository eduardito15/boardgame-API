package com.eduardovecino.boardgame.repository;

import com.eduardovecino.boardgame.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {

    @Query("{ 'user' : ?0 }")
    List<Game> findByUserName(String userName);
}
