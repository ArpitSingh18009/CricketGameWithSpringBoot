package com.example.cricketapplicationdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Document(collection = "Matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Match {

    @Transient
    public static final String SEQUENCE_NAME = "match_sequence";
    @Id
    private int id;
    private String venue;
    private int noOfOvers;
    private String team1Name;
    private String team2Name;
    private int scoreboardId;
    @Indexed
    private String date;

}
