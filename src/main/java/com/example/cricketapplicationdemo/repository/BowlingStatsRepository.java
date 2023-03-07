package com.example.cricketapplicationdemo.repository;

import com.example.cricketapplicationdemo.entity.BowlingStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BowlingStatsRepository extends MongoRepository<BowlingStats,Integer> {
    Optional<BowlingStats> findTopByPlayerIdAndDate(int id, String date);

    Optional<List<BowlingStats>> findAllByPlayerId(int id);
}
