package com.example.cricketapplicationdemo.repository;

import com.example.cricketapplicationdemo.entity.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends MongoRepository<Player,Integer> {
    Optional<Player> findByJersyNo(int jersyNo);
    Optional<Player> findByName(String name);
}
