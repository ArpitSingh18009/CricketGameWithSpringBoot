package com.example.cricketapplicationdemo.controller;

import com.example.cricketapplicationdemo.entity.*;
import com.example.cricketapplicationdemo.repository.TeamRepository;
import com.example.cricketapplicationdemo.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private TeamRepository teamRepository;

    @PostMapping("/post")
    public Player addPlayer(@Valid @RequestBody Player player) // checking validation is not done, getting error in doing so.
    {
        playerService.addPlayer(player);
        return player;
    }

    @GetMapping("/allPlayer")
    public List<Player> getAllPlayer()
    {
        return playerService.getAllPlayer();
    }
    @GetMapping("/player/{id}")
    public Optional<Player> getPlayerById(@PathVariable int id)
    {
        return playerService.getPlayerById(id);
    }

    @GetMapping("/playerName/{name}")
    public Player getPlayerByName(@PathVariable String name)
    {
        return playerService.getPlayerByName(name);
    }
    @GetMapping("/teamPlayer/{id}")
    public List<Player> getAllPlayerOfTeam(@PathVariable int id)
    {
        return playerService.getTeamPlayer(id);
    }
    @PutMapping("update/{id}")
    public String updatePlayer(@PathVariable int id, @RequestBody Player player)
    {
        playerService.updatePlayerById(id,player);
        return "Succesfully updated";
    }

    @DeleteMapping("/delete/{id}")
    public String deletePlayerWithId(@PathVariable int id)
    {
        playerService.deletePlayerWithId(id);
        return "Successfully deleted";
    }

    @GetMapping("/getPlayer/{jersyNo}")
    public Player getPlayerByJersyNo(@PathVariable int jersyNo)
    {

        return playerService.getPlayerByJersyNo(jersyNo).get();
    }

    @GetMapping("/getPlayerStats/{jersyNo}")
    public PlayerStats getPlayerStats(@PathVariable int jersyNo)
    {
        return playerService.getPlayerStatsByJersyNo(jersyNo);
    }
}
