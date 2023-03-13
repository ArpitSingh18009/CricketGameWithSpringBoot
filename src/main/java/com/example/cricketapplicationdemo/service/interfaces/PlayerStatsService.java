package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.BattingStats;
import com.example.cricketapplicationdemo.entity.BowlingStats;
import com.example.cricketapplicationdemo.entity.PlayerStats;

import java.util.Optional;

public interface PlayerStatsService {
    void updateStats(BattingStats battingStats, PlayerStats playerStats);

    void updateBowlingStats(BowlingStats bowlingStats, PlayerStats playerStats);
}
