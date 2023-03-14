package com.example.cricketapplicationdemo.controller;

import com.example.cricketapplicationdemo.entity.*;
import com.example.cricketapplicationdemo.entity.ScoreBoard;
import com.example.cricketapplicationdemo.service.interfaces.MatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/match")
@Slf4j
public class MatchController {
    @Autowired
    private MatchService matchService;

    @PostMapping("/start")
    public ScoreBoard startMatch(@Validated @RequestBody Match match)
    {
        log.info("Start the Match !!!");
        return matchService.startMatch(match);
    }
    @GetMapping("/get/{date}")
    public Match getMatch(@PathVariable String date)
    {
        log.info("Get the match by Date");
        return matchService.getMatchBYDate(date); // It will return match top match on given date;
    }
    @GetMapping("/get-all/{date}")
    public List<Match> getAllMatch(@PathVariable String date)
    {
        log.info("Get all the match played on given Date");
        return matchService.getAllMatchByDate(date); // It will return all match on given date;
    }
    @GetMapping("/fetch/{team1Name}/{team2Name}")
    public Match getMatchByTeamName(@PathVariable String team1Name ,@PathVariable String team2Name )
    {
        log.info("Get the match that is played between given team");
        return matchService.getMatchByTeamName(team1Name,team2Name).get();  // It will return top match between given teams
    }
    @GetMapping("/get/{team1Name}/{team2Name}/{date}")
    public Match getMatchByTeamNameAndDate(@PathVariable String team1Name , @PathVariable String team2Name, @PathVariable String date)
    {
        log.info("Get the match that is played between given team on given date");
        return matchService.getMatchByTeamNameAndDate(team1Name,team2Name,date).get();
    }
}
