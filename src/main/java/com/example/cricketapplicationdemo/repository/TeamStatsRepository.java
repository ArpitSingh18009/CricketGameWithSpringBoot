package com.example.cricketapplicationdemo.repository;

import com.example.cricketapplicationdemo.entity.TeamStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamStatsRepository extends MongoRepository<TeamStats,Integer> {
}
