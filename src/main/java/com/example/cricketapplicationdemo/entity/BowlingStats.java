package com.example.cricketapplicationdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "BowlingStats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndexes({@CompoundIndex(name ="date_playerId", def="{'date':1,'playerId':1}")})
public class BowlingStats {

    @Transient
    public static final String SEQUENCE_NAME = "bowlingStats_sequence";
    @Id
    private int id;
    private int matchId;
    private int playerId;
    @Indexed
    private String date;
    @Indexed
    private String playerName;
    private int runConceded=0;
    private int ballsBowled=0;
    private int totalWicket=0;
    public void increaserunConcede(int run)
    {
        runConceded+=run;
    }
    public void increaseTotalWicket()
    {
        totalWicket++;
    }
    public void increaseBallBowled()
    {
        ballsBowled++;
    }
}
