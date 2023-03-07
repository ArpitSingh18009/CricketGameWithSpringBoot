package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.Team;
import com.example.cricketapplicationdemo.entity.TeamStats;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    void addTeam(Team team);

    List<Team> findAllTeam();

    Optional<Team> findTeamByName(String teamName);

    Optional<TeamStats> findTeamStats(int id);

    void updateTeam(String name, int position, int jersyNo);
}
