package com.example.cricketapplicationdemo.repository;

import com.example.cricketapplicationdemo.entity.ScoreBoard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreBoardRepository extends MongoRepository<ScoreBoard,Integer> {
    ScoreBoard findByMatchId(int id);
}
