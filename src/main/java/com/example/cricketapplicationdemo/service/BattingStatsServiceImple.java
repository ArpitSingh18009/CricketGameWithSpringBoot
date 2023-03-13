package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.*;
import com.example.cricketapplicationdemo.exceptionHandler.ResourceNotFound;
import com.example.cricketapplicationdemo.repository.BattingStatsRepository;
import com.example.cricketapplicationdemo.repository.PlayerRepository;
import com.example.cricketapplicationdemo.repository.PlayerStatsRepository;
import com.example.cricketapplicationdemo.service.interfaces.BattingStatsService;
import com.example.cricketapplicationdemo.service.interfaces.PlayerStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BattingStatsServiceImple implements BattingStatsService {
    @Autowired
    private BattingStatsRepository battingStatsRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private PlayerStatsRepository playerStatsRepository;
    @Autowired
    private PlayerStatsService playerStatsService;

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

    public List<BattingStats> initliaseBatting(List<Integer> battingList , int matchId, String date) {

        List<BattingStats> list = new ArrayList<>();
        for(int index : battingList)
        {
            Optional<Player> player = playerRepository.findById(index);

            BattingStats battingStats =BattingStats.builder()
                    .playerId(index)
                    .matchId(matchId)
                    .playerName(player.get().getName())
                    .date(date)
                    .id(sequenceGeneratorService.generateSequence(BattingStats.SEQUENCE_NAME))
                    .build();

            list.add(battingStats);

        }
        return list;
    }


    public void updateStats(ScoreBoard scoreBoard)
    {
        for(BattingStats battingStats : scoreBoard.getTeam1BattingStats())
        {
            if (battingStats.getBallFaced() > 0) {
                updateBattingStatsOfPlayer(battingStats);
            }
        }
        for(BattingStats battingStats : scoreBoard.getTeam2BattingStats())
        {
            if (battingStats.getBallFaced() > 0) {
               updateBattingStatsOfPlayer(battingStats);
            }
        }
    }
    public void updateBattingStatsOfPlayer(BattingStats battingStats)
    {
        Optional<Player> player;
        Optional<PlayerStats> playerStats;
        player = playerRepository.findById(battingStats.getPlayerId());
        playerRepository.save(player.get());

        playerStats = playerStatsRepository.findById(battingStats.getPlayerId());

        playerStatsService.updateStats(battingStats , playerStats.get());
        playerStatsRepository.save(playerStats.get());
        battingStatsRepository.save(battingStats);
    }

}
