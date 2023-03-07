package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.Match;
import com.example.cricketapplicationdemo.entity.ScoreBoard;

import java.util.List;
import java.util.Optional;

public interface MatchService {
    ScoreBoard startMatch(Match match);

    Match getMatchBYDate(String date);

    Optional<Match> getMatchByTeamName(String team1Name, String team2Name);

    Optional<Match> getMatchByTeamNameAndDate(String team1Name, String team2Name, String date);

    List<Match> getAllMatchByDate(String date);
}
