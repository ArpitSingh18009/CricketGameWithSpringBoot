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

@Document(collection = "BattingStats")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({@CompoundIndex(name ="date_playerId", def="{'date':1,'playerId':1}")})
public class BattingStats {

    @Transient
    public static final String SEQUENCE_NAME = "batiingStats_sequence";
    @Id
    private int id;
    private int matchId;
    private int playerId;
    @Indexed
    private String date;
    @Indexed
    private String playerName;
    private int runScored=0;
    private int ballFaced=0;
    private double strikeRate=0.0;
    private int total4s=0;
    private int total6s=0;

}
