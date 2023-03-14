package com.example.cricketapplicationdemo.controller;

import com.example.cricketapplicationdemo.entity.BattingStats;
import com.example.cricketapplicationdemo.entity.BowlingStats;
import com.example.cricketapplicationdemo.entity.ScoreBoard;
import com.example.cricketapplicationdemo.service.interfaces.BattingStatsService;
import com.example.cricketapplicationdemo.service.interfaces.BowlingStatsService;
import com.example.cricketapplicationdemo.service.interfaces.ScoreboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
@Slf4j
public class StatsController {
    @Autowired
    private BattingStatsService battingStatsService;
    @Autowired
    private BowlingStatsService bowlingStatsService;
    @Autowired
    private ScoreboardService scoreboardService;

    @GetMapping("/batting/{jersyNo}/{date}")
    public BattingStats getBattingStatsByJersyNoAndDate(@PathVariable int jersyNo , @PathVariable String date)
    {
        log.info("Get BattingStats by jersyNo and date");
        return battingStatsService.getBattingStatsByJersyNoAndDate(jersyNo,date);
    }

    @GetMapping("/bowling/{jersyNo}/{date}")
    public BowlingStats getBowlingStats(@PathVariable int jersyNo , @PathVariable String date)
    {
        log.info("Get BowlingStats by jersyNo and date");
        return bowlingStatsService.getBowlingStatsByJersyNoAndDate(jersyNo,date);
    }

    @GetMapping("/bowlingStats/{jersyNo}")
    public List<BowlingStats> getBowlingStats(@PathVariable int jersyNo)
    {
        log.info("Get All BowlingStats of player by jersyNo ");
        return bowlingStatsService.getAllBowlingStats(jersyNo);
    }
    @GetMapping("/battingStats/{jersyNo}")
    public List<BattingStats> getBattingStats(@PathVariable int jersyNo)
    {
        log.info("Get All BattingStats by jersyNo");
        return battingStatsService.getAllBattingStats(jersyNo);
    }
    @GetMapping("/scoreboard/{date}")
    public ScoreBoard getScoreboardBydate(@PathVariable String date)
    {
        log.info("Get ScoreBoard by date ");
        return scoreboardService.getScoreboardByDate(date);
    }
}
