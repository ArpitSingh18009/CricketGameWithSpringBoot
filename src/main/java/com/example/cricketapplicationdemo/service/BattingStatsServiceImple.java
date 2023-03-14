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
    /*
          Initialising the BattingStats for each Player who is playing the inning and later store into the
          database.
     */
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
    /*
        Updating the BattingStats of player after each ball is played
     */
    void increaseRunScored(int run,BattingStats battingStats)
    {
        battingStats.setRunScored(battingStats.getRunScored() + run);
        if(run == 4)
            battingStats.setTotal4s(battingStats.getTotal4s() + 1);
        else if(run == 6)
            battingStats.setTotal6s(battingStats.getTotal6s() + 1);
    }
    /*
        Updating the bowl faced by batsman after each ball.
     */
    void increseBallFaced(BattingStats battingStats)
    {
        battingStats.setBallFaced(battingStats.getBallFaced() + 1) ;
    }

    /*
         Update the BattingStats of player after the match is completed
     */
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

    /*
         Make this function private because nobody from outside can use it
         Only class can access it.
     */
    private void updateBattingStatsOfPlayer(BattingStats battingStats)
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
