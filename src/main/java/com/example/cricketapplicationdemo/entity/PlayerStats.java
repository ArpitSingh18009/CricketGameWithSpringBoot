package com.example.cricketapplicationdemo.entity;

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

    public void updateStats(BattingStats battingStats)
    {
        this.totalRunsScored +=battingStats.getRunScored();
        this.totalBallsFaced +=battingStats.getBallFaced();
        this.total4s += battingStats.getTotal4s();
        this.total6s += battingStats.getTotal6s();
        this.strikeRate = (double)(totalRunsScored/totalBallsFaced)*100;
    }
    public void updateBowlingStats(BowlingStats bowlingStats)
    {
        this.totalBallsBowled +=bowlingStats.getBallsBowled();
        this.totalWicketTaken +=bowlingStats.getTotalWicket();

    }
}
