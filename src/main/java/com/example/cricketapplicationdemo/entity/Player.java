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


@Document(collection = "Player")
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

    @Transient
    public static final String SEQUENCE_NAME = "player_sequence";
    @Id
    private int id;
    @NotBlank
    private String name;
    @NotNull
    @Indexed
    private int jersyNo;
    private int playerStatsId;

}
