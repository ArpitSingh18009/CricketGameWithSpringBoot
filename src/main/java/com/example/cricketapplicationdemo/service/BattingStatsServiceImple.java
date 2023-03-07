package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.BattingStats;
import com.example.cricketapplicationdemo.entity.Player;
import com.example.cricketapplicationdemo.exceptionHandler.ResourceNotFound;
import com.example.cricketapplicationdemo.repository.BattingStatsRepository;
import com.example.cricketapplicationdemo.repository.PlayerRepository;
import com.example.cricketapplicationdemo.service.interfaces.BattingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BattingStatsServiceImple implements BattingStatsService {
    @Autowired
    private BattingStatsRepository battingStatsRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public BattingStats getBattingStatsByJersyNoAndDate(int jersyNo, String date) {
        Player player = playerRepository.findByJersyNo(jersyNo).orElseThrow(() ->
                new ResourceNotFound("Player not found with given jersyNo "));
        int id = player.getId();

        BattingStats battingStats = (BattingStats) Optional.of(battingStatsRepository.findTopByPlayerIdAndDate(id,date)).orElseThrow( () ->
                new ResourceNotFound("No Batting Stats found by given date and jersyNo"));

        return battingStats;
    }

    @Override
    public List<BattingStats> getAllBattingStats(int jersyNo) {
        Player player = playerRepository.findByJersyNo(jersyNo).orElseThrow(() ->
                new ResourceNotFound("Player not found with given jersyNo "));
        int id = player.getId();


         return battingStatsRepository.findAllByPlayerId(id).orElseThrow(()->
                 new ResourceNotFound("No battingStats found to given jersyNo"));

    }
}
