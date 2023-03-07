package com.example.cricketapplicationdemo.helper;


import com.example.cricketapplicationdemo.entity.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Ball {
    private int overNumber;
    private int ballNumber;
    private String bowler;
    private String batsman;
    private String run;

    private int Inning;

    public Ball(int overNumber, int ballNumber, String batsman, String bowler, int run, int Inning) {
        this.overNumber = overNumber;
        this.ballNumber = ballNumber;
        this.bowler = bowler;
        this.batsman = batsman;
        if (run > 6)
            this.run = "W";
        else
            this.run = Integer.toString(run);
        this.Inning = Inning;
    }
}
