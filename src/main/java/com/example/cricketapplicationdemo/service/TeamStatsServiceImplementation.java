package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.ScoreBoard;
import com.example.cricketapplicationdemo.entity.Team;
import com.example.cricketapplicationdemo.entity.TeamStats;
import com.example.cricketapplicationdemo.repository.TeamStatsRepository;
import com.example.cricketapplicationdemo.service.interfaces.TeamStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamStatsServiceImplementation implements TeamStatsService {
    @Autowired
    private TeamStatsRepository teamStatsRepository;

    public void updateTeamStats(ScoreBoard scoreBoard , Team team1, Team team2) {
        Optional<TeamStats> teamStats = teamStatsRepository.findById(team1.getId());
        increaseNoOfMatchesPlayed(teamStats.get());
        if (scoreBoard.getWinner() == team1.getName()) {
            increaseNoOfMatchesWin(teamStats.get());
        } else {
            increaseNoOfMatchesLost(teamStats.get());
        }
        teamStatsRepository.save(teamStats.get());

        teamStats = teamStatsRepository.findById(team2.getId());
        increaseNoOfMatchesPlayed(teamStats.get());
        if(scoreBoard.getWinner() == team2.getName())
        {
            increaseNoOfMatchesWin(teamStats.get());
        } else {
           increaseNoOfMatchesLost(teamStats.get());
        }
        teamStatsRepository.save(teamStats.get());
    }
    void increaseNoOfMatchesPlayed(TeamStats teamStats)
    {
        teamStats.setNoOfmatchesPlayed(teamStats.getNoOfmatchesPlayed() + 1);
    }
    void increaseNoOfMatchesWin(TeamStats teamStats)
    {
        teamStats.setNoOfmatchesWon(teamStats.getNoOfmatchesWon()+1);
    }
    void increaseNoOfMatchesLost(TeamStats teamStats)
    {
        teamStats.setNoOfMatchesLost(teamStats.getNoOfMatchesLost() + 1);
    }
}
