package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.Player;
import com.example.cricketapplicationdemo.entity.Team;
import com.example.cricketapplicationdemo.entity.TeamStats;
import com.example.cricketapplicationdemo.exceptionHandler.ResourceNotFound;
import com.example.cricketapplicationdemo.repository.PlayerRepository;
import com.example.cricketapplicationdemo.repository.TeamRepository;
import com.example.cricketapplicationdemo.repository.TeamStatsRepository;
import com.example.cricketapplicationdemo.service.interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TeamServiceImplementation implements TeamService {
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private TeamStatsRepository teamStatsRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public void addTeam(Team team) {

        List<Integer> ids = new ArrayList<>();
        for(int index : team.getPlayerIds())
        {
            Optional<Player> player = Optional.of(playerRepository.findByJersyNo(index)).orElseThrow(() ->
                    new ResourceNotFound("Player with given JersyNo " + index + " does not found" ));

            if(player.isPresent())
                ids.add(player.get().getId());
        }
        team.setId(sequenceGeneratorService.generateSequence(team.SEQUENCE_NAME));
        team.setTeamStatsId(team.getId());
        team.setPlayerIds(ids);
        TeamStats teamStats = new TeamStats();
        teamStats.setId(team.getId());
        teamStats.setTeamId(team.getId());
        teamStatsRepository.save(teamStats);
        teamRepository.save(team);
    }

    @Override
    public List<Team> findAllTeam() {
        return teamRepository.findAll();
    }

    @Override
    public Optional<Team> findTeamByName(String teamName) {
        return Optional.ofNullable(teamRepository.findByName(teamName).orElseThrow(() ->
                new ResourceNotFound("Team with this teamName " + teamName + " does not exist")));
    }

    @Override
    public Optional<TeamStats> findTeamStats(int id) {
        return teamStatsRepository.findById(id);
    }

    @Override
    public void updateTeam(String name, int position, int jersyNo) {
        Team team = (teamRepository.findByName(name).orElseThrow(()->
                new ResourceNotFound("Team with given name does not found")));

        Player player = (playerRepository.findByJersyNo(jersyNo).orElseThrow(() ->
                new ResourceNotFound("Player with given jersyNo does not found")));

        int id = player.getId();
        for(int index : team.getPlayerIds())
        {
            if(Objects.equals(index ,id))
                throw new ResourceNotFound("Player with given jersyNo already exist in team");
        }
        team.getPlayerIds().add((int)position-1,id);
        teamRepository.save(team);
    }
}
