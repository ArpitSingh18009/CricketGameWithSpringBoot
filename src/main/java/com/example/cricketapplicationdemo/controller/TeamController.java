package com.example.cricketapplicationdemo.controller;

import com.example.cricketapplicationdemo.entity.Team;
import com.example.cricketapplicationdemo.entity.TeamStats;
import com.example.cricketapplicationdemo.service.interfaces.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/")
    public String check()
    {
        return "Team Controller Working";
    }

    @PostMapping("/addTeam")
    public Team createTeam(@RequestBody Team team)
    {
        teamService.addTeam(team);
        return team;
    }
    @GetMapping("/getTeam")
    public List<Team> getTeam()
    {
        return teamService.findAllTeam();
    }

    @GetMapping("/getTeam/{teamName}")
    public Team getTeamByName(@PathVariable String teamName)
    {
        return (teamService.findTeamByName(teamName)).get();
    }
    @GetMapping("/getTeamStats/{teamName}")
    public Optional<TeamStats> getTeamStats(@PathVariable String teamName)
    {
        Team team = teamService.findTeamByName(teamName).get();

        return teamService.findTeamStats(team.getTeamStatsId());
    }
    @PutMapping("/changeTeam/{name}/{position}/{jersyNo}")
    public String updateTeam(@PathVariable String name , @PathVariable int position, @PathVariable int jersyNo)
    {
        teamService.updateTeam(name, position, jersyNo);
        return  "Successfully Updated";
    }

}
