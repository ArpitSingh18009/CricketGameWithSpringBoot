package com.example.cricketapplicationdemo.repository;

import com.example.cricketapplicationdemo.entity.PlayerStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatsRepository extends MongoRepository<PlayerStats,Integer> {
}
