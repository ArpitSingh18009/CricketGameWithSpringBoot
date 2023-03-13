package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.*;
import com.example.cricketapplicationdemo.exceptionHandler.ResourceNotFound;
import com.example.cricketapplicationdemo.repository.BowlingStatsRepository;
import com.example.cricketapplicationdemo.repository.PlayerRepository;
import com.example.cricketapplicationdemo.repository.PlayerStatsRepository;
import com.example.cricketapplicationdemo.service.interfaces.BowlingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BowlingStatsServiceImple implements BowlingStatsService {
    @Autowired
    private BowlingStatsRepository bowlingStatsRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private PlayerStatsRepository playerStatsRepository;

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
    public List<BowlingStats> initliaseBowling(List<Integer> bowlingList,int matchId , String date) {
        List<BowlingStats> list = new ArrayList<>();
        for(int index : bowlingList)
        {

            Optional<Player> player = playerRepository.findById(index);

            BowlingStats bowlingStats = BowlingStats.builder()
                    .playerId(index)
                    .matchId(matchId)
                    .playerName(player.get().getName())
                    .date(date)
                    .id(sequenceGeneratorService.generateSequence(BattingStats.SEQUENCE_NAME))
                    .build();

            list.add(bowlingStats);
        }
        return list;
    }
    public void updateStats(ScoreBoard scoreBoard)
    {
        for(BowlingStats bowlingStats : scoreBoard.getTeam1BowlingStats())
        {
            if (bowlingStats.getBallsBowled() > 0) {
               updateBowlingStatsOfPlayer(bowlingStats);
            }
        }
        for(BowlingStats bowlingStats : scoreBoard.getTeam2BowlingStats())
        {
            if (bowlingStats.getBallsBowled() > 0) {
               updateBowlingStatsOfPlayer(bowlingStats);
            }
        }
    }
    public void updateBowlingStatsOfPlayer(BowlingStats bowlingStats)
    {
        Optional<Player> player;
        Optional<PlayerStats> playerStats;

        player = playerRepository.findById(bowlingStats.getPlayerId());
        playerRepository.save(player.get());

        playerStats = playerStatsRepository.findById(bowlingStats.getPlayerId());
        playerStats.get().updateBowlingStats(bowlingStats);
        playerStatsRepository.save(playerStats.get());

        bowlingStatsRepository.save(bowlingStats);
    }
}
