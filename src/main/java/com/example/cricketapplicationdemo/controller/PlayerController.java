package com.example.cricketapplicationdemo.controller;

import com.example.cricketapplicationdemo.entity.*;
import com.example.cricketapplicationdemo.repository.TeamRepository;
import com.example.cricketapplicationdemo.service.interfaces.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/player")
@Slf4j
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamRepository teamRepository;


    @PostMapping("/add")
    public Player addPlayer(@Validated @RequestBody Player player) // checking validation is not done, getting error in doing so.
    {
        log.info(" Add player into the database");
        playerService.addPlayer(player);
        return player;
    }

    @GetMapping("/all")
    public List<Player> getAllPlayer()
    {
        log.info("Get All player");
        return playerService.getAllPlayer();
    }
    @GetMapping("/get-by-id/{id}")
    public Optional<Player> getPlayerById(@PathVariable int id)
    {
        log.info("Get Player by Id");
        return playerService.getPlayerById(id);
    }

    @GetMapping("/name/{name}")
    public Player getPlayerByName(@PathVariable String name)
    {
        log.info("Get Player by Name");
        return playerService.getPlayerByName(name);
    }
    @GetMapping("/team/{id}")
    public List<Player> getAllPlayerOfTeam(@PathVariable int id)
    {
        log.info("Get All Player of Given Team");
        return playerService.getTeamPlayer(id);
    }
    @PutMapping("update/{id}")
    public String updatePlayer(@PathVariable int id, @RequestBody Player player)
    {
        log.info("Update the Player with given Id");
        playerService.updatePlayerById(id,player);
        return "Succesfully updated";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePlayerWithId(@PathVariable int id)
    {
        log.info("Delete Player with the given Id");
        playerService.deletePlayerWithId(id);
        return "Successfully deleted";
    }

    @GetMapping("/get-by-jersyNo/{jersyNo}")
    public Player getPlayerByJersyNo(@PathVariable int jersyNo)
    {
        log.info("Get Player by given jersyNo");
        return playerService.getPlayerByJersyNo(jersyNo).get();
    }

    @GetMapping("/get-stats/{jersyNo}")
    public PlayerStats getPlayerStats(@PathVariable int jersyNo)
    {
        log.info("Get the PlayerStats by given JersyNo");
        return playerService.getPlayerStatsByJersyNo(jersyNo);
    }
}
