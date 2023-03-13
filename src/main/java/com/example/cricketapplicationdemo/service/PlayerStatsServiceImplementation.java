package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.BattingStats;
import com.example.cricketapplicationdemo.entity.BowlingStats;
import com.example.cricketapplicationdemo.entity.PlayerStats;
import com.example.cricketapplicationdemo.repository.PlayerStatsRepository;
import com.example.cricketapplicationdemo.service.interfaces.PlayerStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerStatsServiceImplementation implements PlayerStatsService {
    @Autowired
    private PlayerStatsRepository playerStatsRepository;

    public void updateStats(BattingStats battingStats, PlayerStats playerStats)
    {
        playerStats.setTotalRunsScored(playerStats.getTotalRunsScored() + battingStats.getRunScored());
        playerStats.setTotalBallsFaced(playerStats.getTotalBallsFaced() + battingStats.getBallFaced());
        playerStats.setTotal4s(playerStats.getTotal4s() + battingStats.getTotal4s());
        playerStats.setTotal6s(playerStats.getTotal6s() + battingStats.getTotal4s());
    }
    public void updateBowlingStats(BowlingStats bowlingStats , PlayerStats playerStats)
    {
        playerStats.setTotalBallsBowled(playerStats.getTotalBallsBowled() + bowlingStats.getBallsBowled());
        playerStats.setTotalWicketTaken(playerStats.getTotalWicketTaken() + bowlingStats.getTotalWicket());
    }

}
