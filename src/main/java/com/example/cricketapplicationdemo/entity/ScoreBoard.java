package com.example.cricketapplicationdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.*;

@Document(collection = "ScoreBoard")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ScoreBoard {

    @Id
    private int id;
    @Indexed
    private int matchId;
    private String team1Name;
    private String team2Name;
    private String battingTeamName ;
    private int scoreOfFirstTeam;
    private int wicketOfFirstTeam;
    private List<BattingStats> team1BattingStats ;
    private List<BowlingStats> team2BowlingStats;

    private int scoreOfSecondTeam;
    private int wicketOfSecondTeam;
    private List<BattingStats> team2BattingStats;
    private List<BowlingStats> team1BowlingStats;
    public ScoreBoard(int matchId,String team1Name, String team2Name)
    {
        this.id=matchId;
        this.matchId=matchId;
        this.team1Name = team1Name;
        this.team2Name = team2Name;
    }
    private String winner ;
}
