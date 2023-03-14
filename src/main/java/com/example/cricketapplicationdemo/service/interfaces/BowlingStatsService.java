package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.BowlingStats;
import com.example.cricketapplicationdemo.entity.ScoreBoard;

import java.util.List;

public interface BowlingStatsService {
    BowlingStats getBowlingStatsByJersyNoAndDate(int jersyNo, String date);

    List<BowlingStats> getAllBowlingStats(int jersyNo);

    List<BowlingStats> initliaseBowling(List<Integer> bowlingList, int id, String date);

    void updateStats(ScoreBoard scoreBoard);
}
