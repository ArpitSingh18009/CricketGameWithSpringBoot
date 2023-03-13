package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.BattingStats;
import com.example.cricketapplicationdemo.entity.BowlingStats;
import com.example.cricketapplicationdemo.entity.ScoreBoard;

import java.util.List;

public interface ScoreboardService {
    ScoreBoard getScoreboardByDate(String date);

    void updateScoreboard(ScoreBoard scoreBoard, int inningCount, int total, int wicket, List<BattingStats> team1BattingStats, List<BowlingStats> team2BowlingStats);
}
