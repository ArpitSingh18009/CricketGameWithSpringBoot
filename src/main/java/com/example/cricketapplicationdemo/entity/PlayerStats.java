package com.example.cricketapplicationdemo.entity;

import com.example.cricketapplicationdemo.service.interfaces.PlayerStatsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "PlayerStata")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PlayerStats {
    @Id
    private int id;
    private int playerId;

    // Batting Record
    private int totalRunsScored=0;
    private int totalBallsFaced=0;
    private int total4s=0;
    private int total6s=0;
    private double strikeRate=0.0;


    // Bowling Record
    private int totalWicketTaken=0;
    private int totalBallsBowled=0;

}
