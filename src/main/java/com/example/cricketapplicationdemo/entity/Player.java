package com.example.cricketapplicationdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.*;

@Document(collection = "Player")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Player {

    @Transient
    public static final String SEQUENCE_NAME = "player_sequence";
    @Id
    private int id;
    @NotNull
    private String name;
    @NotNull
    @Indexed
    private int jersyNo;
    private int playerStatsId;

}
