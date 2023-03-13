package com.example.cricketapplicationdemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {

    @Transient
    public static final String SEQUENCE_NAME = "match_sequence";
    @Id
    private int id;
    @NotBlank
    private String venue;
    private int noOfOvers;
    @NotBlank
    private String team1Name;
    @NotBlank
    private String team2Name;
    private int scoreboardId;
    @Indexed
    private String date;

}
