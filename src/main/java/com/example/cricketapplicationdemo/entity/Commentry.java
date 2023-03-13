package com.example.cricketapplicationdemo.entity;

import com.example.cricketapplicationdemo.helper.Ball;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Commentry")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Commentry {

    @Transient
    public static final String SEQUENCE_NAME = "commentry_sequence";
    @Id
    private int id;
    private int matchId;
    private int teamId;
    private List<Ball> result;


}
