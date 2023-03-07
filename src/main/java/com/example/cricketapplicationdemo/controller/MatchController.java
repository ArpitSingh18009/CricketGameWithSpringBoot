package com.example.cricketapplicationdemo.controller;

import com.example.cricketapplicationdemo.entity.*;
import com.example.cricketapplicationdemo.entity.ScoreBoard;
import com.example.cricketapplicationdemo.service.interfaces.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @PostMapping("/startMatch")
    public ScoreBoard startMatch(@RequestBody Match match)
    {
        return matchService.startMatch(match);
    }
    @GetMapping("/getMatch/{date}")
    public Match getMatch(@PathVariable String date)
    {
        return matchService.getMatchBYDate(date); // It will return match top match on given date;
    }
    @GetMapping("/getAllMatch/{date}")
    public List<Match> getAllMatch(@PathVariable String date)
    {
        return matchService.getAllMatchByDate(date); // It will return all match on given date;
    }
    @GetMapping("/fetchMatch/{team1Name}/{team2Name}")
    public Match getMatchByTeamName(@PathVariable String team1Name ,@PathVariable String team2Name )
    {
        return matchService.getMatchByTeamName(team1Name,team2Name).get();  // It will return top match between given teams
    }
    @GetMapping("/getMatch/{team1Name}/{team2Name}/{date}")
    public Match getMatchByTeamNameAndDate(@PathVariable String team1Name , @PathVariable String team2Name, @PathVariable String date)
    {

        return matchService.getMatchByTeamNameAndDate(team1Name,team2Name,date).get();
    }
}
