package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.BattingStats;

import java.util.List;

public interface BattingStatsService {
    BattingStats getBattingStatsByJersyNoAndDate(int jersyNo, String date);

    List<BattingStats> getAllBattingStats(int jersyNo);
}
