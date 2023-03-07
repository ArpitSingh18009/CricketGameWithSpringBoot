package com.example.cricketapplicationdemo.repository;

import com.example.cricketapplicationdemo.entity.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends MongoRepository<Team,Integer> {
    Optional<Team> findByName(String team2Name);
}
