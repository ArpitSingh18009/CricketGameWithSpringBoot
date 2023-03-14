package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.ScoreBoard;
import com.example.cricketapplicationdemo.entity.Team;

public interface TeamStatsService {
    void updateTeamStats(ScoreBoard scoreBoard, Team team1, Team team2);
}
