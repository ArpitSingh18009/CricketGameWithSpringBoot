package com.example.cricketapplicationdemo.repository;

import com.example.cricketapplicationdemo.entity.BattingStats;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.lang.module.ModuleFinder;
import java.util.List;
import java.util.Optional;

@Repository
public interface BattingStatsRepository extends MongoRepository<BattingStats,Integer> {
    BattingStats findByPlayerIdAndDate(int id, String date);

    BattingStats findTopByPlayerIdAndDate(int id, String date);

    Optional<List<BattingStats>> findAllByPlayerId(int id);
}
