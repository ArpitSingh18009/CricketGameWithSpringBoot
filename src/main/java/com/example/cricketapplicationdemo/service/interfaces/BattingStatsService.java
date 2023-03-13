package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.BattingStats;
import com.example.cricketapplicationdemo.entity.ScoreBoard;

import java.util.List;

public interface BattingStatsService {
    BattingStats getBattingStatsByJersyNoAndDate(int jersyNo, String date);

    List<BattingStats> getAllBattingStats(int jersyNo);

    List<BattingStats> initliaseBatting(List<Integer> battingList, int id, String date);

    void updateBattingStatsOfPlayer(BattingStats battingStats);

    void updateStats(ScoreBoard scoreBoard);
}
