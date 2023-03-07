package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.Player;
import com.example.cricketapplicationdemo.entity.PlayerStats;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    void addPlayer(Player player);

    List<Player> getAllPlayer();

    Optional<Player> getPlayerById(int id);

    void updatePlayerById(int id, Player player);

    void deletePlayerWithId(int id);

    List<Player> getTeamPlayer(int id);

    Optional<Player> getPlayerByJersyNo(int jersyNo);

    PlayerStats getPlayerStatsByJersyNo(int jersyNo);
}
