package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.BowlingStats;
import com.example.cricketapplicationdemo.entity.Player;
import com.example.cricketapplicationdemo.exceptionHandler.ResourceNotFound;
import com.example.cricketapplicationdemo.repository.BowlingStatsRepository;
import com.example.cricketapplicationdemo.repository.PlayerRepository;
import com.example.cricketapplicationdemo.service.interfaces.BowlingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BowlingStatsServiceImple implements BowlingStatsService {
    @Autowired
    private BowlingStatsRepository bowlingStatsRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public BowlingStats getBowlingStatsByJersyNoAndDate(int jersyNo, String date) {
        Player player = playerRepository.findByJersyNo(jersyNo).orElseThrow(() ->
                new ResourceNotFound("Player not found with given jersyNo "));
        int id = player.getId();

        BowlingStats bowlingStats = bowlingStatsRepository.findTopByPlayerIdAndDate(id, date).orElseThrow( () ->
                new ResourceNotFound("No Batting Stats found by given date and jersyNo"));
        return bowlingStats;
    }

    @Override
    public List<BowlingStats> getAllBowlingStats(int jersyNo) {
        Player player = playerRepository.findByJersyNo(jersyNo).orElseThrow(() ->
                new ResourceNotFound("Player not found with given jersyNo "));
        int id = player.getId();

        return bowlingStatsRepository.findAllByPlayerId(id).orElseThrow(() ->
                new ResourceNotFound("No bowlingStats found for given jersyNo"));
    }
}
