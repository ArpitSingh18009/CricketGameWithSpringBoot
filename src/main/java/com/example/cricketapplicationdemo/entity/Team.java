package com.example.cricketapplicationdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.*;

@Document(collection = "Teams")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Team {
    @Transient
    public static final String SEQUENCE_NAME = "team_sequence";
    @Id
    private int id;
    @Indexed
    private String name;
    private int teamStatsId;
    private List<Integer> playerIds;
}
