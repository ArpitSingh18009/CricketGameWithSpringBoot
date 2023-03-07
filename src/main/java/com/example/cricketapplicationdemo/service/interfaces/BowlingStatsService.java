package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.BowlingStats;

import java.util.List;

public interface BowlingStatsService {
    BowlingStats getBowlingStatsByJersyNoAndDate(int jersyNo, String date);

    List<BowlingStats> getAllBowlingStats(int jersyNo);
}
