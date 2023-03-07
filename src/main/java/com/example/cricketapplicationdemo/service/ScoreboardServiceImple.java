package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.Match;
import com.example.cricketapplicationdemo.entity.ScoreBoard;
import com.example.cricketapplicationdemo.exceptionHandler.ResourceNotFound;
import com.example.cricketapplicationdemo.repository.MatchRepository;
import com.example.cricketapplicationdemo.repository.ScoreBoardRepository;
import com.example.cricketapplicationdemo.service.interfaces.ScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
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
}
