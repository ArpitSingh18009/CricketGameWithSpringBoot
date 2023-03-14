package com.example.cricketapplicationdemo.controller;

import com.example.cricketapplicationdemo.entity.Team;
import com.example.cricketapplicationdemo.entity.TeamStats;
import com.example.cricketapplicationdemo.service.interfaces.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
@Slf4j
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/")
    public String check()
    {
        log.info("Working ");
        return "Team Controller Working";
    }

    @PostMapping("/add")
    public Team createTeam(@RequestBody Team team)
    {
        log.info("Add Team to the database");
        teamService.addTeam(team);
        return team;
    }
    @GetMapping("/get")
    public List<Team> getTeam()
    {
        log.info("Get the list of all teams");
        return teamService.findAllTeam();
    }

    @GetMapping("/get/{teamName}")
    public Team getTeamByName(@PathVariable String teamName)
    {
        log.info("Get the Team by there team Name");
        return (teamService.findTeamByName(teamName)).get();
    }
    @GetMapping("/getTeamStats/{teamName}")
    public Optional<TeamStats> getTeamStats(@PathVariable String teamName)
    {
        Team team = teamService.findTeamByName(teamName).get();
        log.info("Get TeamStats by there TeamName ");
        return teamService.findTeamStats(team.getTeamStatsId());
    }
    // Use dto and request body, convert it to post and give explanation.
    @PutMapping("/change/{name}/{position}/{jersyNo}")
    public String updateTeam(@PathVariable String name , @PathVariable int position, @PathVariable int jersyNo)
    {
        log.info("Updating the team with the given player jersyNo");
        teamService.updateTeam(name, position, jersyNo);
        return  "Successfully Updated";
    }

}
