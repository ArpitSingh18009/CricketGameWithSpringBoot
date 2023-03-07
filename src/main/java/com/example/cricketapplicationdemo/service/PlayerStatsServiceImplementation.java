package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.repository.PlayerStatsRepository;
import com.example.cricketapplicationdemo.service.interfaces.PlayerStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerStatsServiceImplementation implements PlayerStatsService {
    @Autowired
    private PlayerStatsRepository playerStatsRepository;
}
