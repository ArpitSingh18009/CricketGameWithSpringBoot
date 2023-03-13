package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.Player;
import com.example.cricketapplicationdemo.entity.PlayerStats;
import com.example.cricketapplicationdemo.entity.Team;
import com.example.cricketapplicationdemo.exceptionHandler.ResourceNotFound;
import com.example.cricketapplicationdemo.repository.PlayerRepository;
import com.example.cricketapplicationdemo.repository.PlayerStatsRepository;
import com.example.cricketapplicationdemo.repository.TeamRepository;
import com.example.cricketapplicationdemo.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerServiceImplementation implements PlayerService {
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerStatsRepository playerStatsRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    @Override
    public void addPlayer(Player player) {
        // First Check Player with jersyNo already exists or not

        Optional<Player> playerOptional = playerRepository.findByJersyNo(player.getJersyNo());
        if(playerOptional.isPresent())
            throw new ResourceNotFound("Player with this jersyNo "+ player.getJersyNo() +" already exist");

        player.setId(sequenceGeneratorService.generateSequence(player.SEQUENCE_NAME));
        player.setPlayerStatsId(player.getId());
        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayerId(player.getId());
        playerStats.setId(player.getId());
        playerRepository.save(player);
        playerStatsRepository.save(playerStats);
    }

    @Override
    public List<Player> getAllPlayer() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> getPlayerById(int id) {
        Optional<Player> player = Optional.of(playerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound("No player found with " + id )));
        return player;
    }

    @Override
    public void updatePlayerById(int id, Player player) {

        Optional<Player> optionalPlayer = Optional.of(playerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound("Player with this playerId " + id + " does not exist" )));

        if(player.getName() != null)
            optionalPlayer.get().setName(player.getName());

        playerRepository.save(optionalPlayer.get());
    }
    @Override
    public void deletePlayerWithId(int id) {
        Optional<Player> player = Optional.of(playerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound("Player with playerId " + id + " does not exist")));
        playerRepository.deleteById(id);
    }
    @Override
    public List<Player> getTeamPlayer(int id) {

        Optional<Team> team = Optional.of(teamRepository.findById(id).orElseThrow(() ->
                new ResourceNotFound("Team with teamId " + id + " does not exist")));

        List<Integer> list = teamRepository.findById(id).get().getPlayerIds();

        List<Player> playersList= new ArrayList<>();
        for(int i = 0; i<11 ; i++)
        {
            playersList.add(playerRepository.findById(list.get(i)).get());
        }

        return playersList;

    }
    @Override
    public Optional<Player> getPlayerByJersyNo(int jersyNo) {
        Optional<Player> player = Optional.of(playerRepository.findByJersyNo(jersyNo)).orElseThrow(() ->
                new ResourceNotFound("Player with this jersyNo not found"));
        return player;
    }
    @Override
    public PlayerStats getPlayerStatsByJersyNo(int jersyNo) {
        int playerId = playerRepository.findByJersyNo(jersyNo).get().getPlayerStatsId();
        return playerStatsRepository.findById(playerId).get();
    }

    @Override
    public Player getPlayerByName(String name) {
        return playerRepository.findByName(name).orElseThrow(()->
                new ResourceNotFound("Player with given name "+ name +" not found")) ;
    }
}
