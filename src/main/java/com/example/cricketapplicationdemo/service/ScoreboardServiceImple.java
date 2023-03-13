package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.BattingStats;
import com.example.cricketapplicationdemo.entity.BowlingStats;
import com.example.cricketapplicationdemo.entity.Match;
import com.example.cricketapplicationdemo.entity.ScoreBoard;
import com.example.cricketapplicationdemo.exceptionHandler.ResourceNotFound;
import com.example.cricketapplicationdemo.repository.MatchRepository;
import com.example.cricketapplicationdemo.repository.ScoreBoardRepository;
import com.example.cricketapplicationdemo.service.interfaces.ScoreboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class ScoreboardServiceImple implements ScoreboardService {
    @Autowired
    private ScoreBoardRepository scoreBoardRepository;
    @Autowired
    private MatchRepository matchRepository;

    @Override
    public ScoreBoard getScoreboardByDate(String date) {
        Match match = Optional.of(matchRepository.findTopByDate(date)).orElseThrow(()->
                new ResourceNotFound("No Scoreboard found to the given date"));

        return scoreBoardRepository.findByMatchId(match.getId());
    }
    public void updateScoreboard(ScoreBoard scoreBoard , int inningCount , int total , int wicket , List<BattingStats> team1BattingStats, List<BowlingStats> team2BowlingStats)
    {
        if(Objects.equals(inningCount, 1))
        {
            scoreBoard.setScoreOfFirstTeam(total);
            scoreBoard.setWicketOfFirstTeam(wicket);
            scoreBoard.setTeam1BattingStats(team1BattingStats);
            scoreBoard.setTeam2BowlingStats(team2BowlingStats);
        }
        else
        {
            scoreBoard.setScoreOfSecondTeam(total);
            scoreBoard.setWicketOfSecondTeam(wicket);
            scoreBoard.setTeam2BattingStats(team1BattingStats);
            scoreBoard.setTeam1BowlingStats(team2BowlingStats);
        }
    }
}
