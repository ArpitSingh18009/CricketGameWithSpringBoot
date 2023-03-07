package com.example.cricketapplicationdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TeamStats")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamStats {
    @Id
    private int id;
    private int teamId;
    private int noOfmatchesPlayed=0;
    private int noOfmatchesWon=0;
    private int noOfMatchesLost=0;
    private int noOfMatchesDraw=0;

    public void increaseNoOfMatchesPlayed()
    {
        noOfmatchesPlayed++;
    }
    public void increaseNoOfMatchesWin()
    {
        noOfmatchesWon++;
    }
    public void increaseNoOfMatchesLost()
    {
        noOfMatchesLost++;
    }
    public void increaseNoOfMatchesDraw()
    {
        noOfMatchesDraw++;
    }
}
