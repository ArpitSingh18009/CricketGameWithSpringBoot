package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.*;
import com.example.cricketapplicationdemo.exceptionHandler.ResourceNotFound;
import com.example.cricketapplicationdemo.helper.Ball;
import com.example.cricketapplicationdemo.repository.*;
import com.example.cricketapplicationdemo.service.interfaces.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.lang.Math.max;



@Service
@Slf4j
public class MatchServiceImplementation implements MatchService {
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private ScoreBoardRepository scoreBoardRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    TeamStatsRepository teamStatsRepository;
    @Autowired
    private CommentryRepository commentryRepository;
    @Autowired
    private BattingStatsService battingStatsService;
    @Autowired
    private BowlingStatsService bowlingStatsService;
    @Autowired
    private ScoreboardService scoreboardService;
    @Autowired
    private CommentryService commentryService;


    private Match match;
    private ScoreBoard scoreBoard;
    private Team team1;
    private Team team2;
    private String date="";

    @Override
    public ScoreBoard startMatch(Match match) {

        Optional<Team> team = Optional.ofNullable(teamRepository.findByName(match.getTeam1Name()).orElseThrow(() ->
                new ResourceNotFound("Team with teamId " + match.getTeam1Name() + " does not found")));
        Team teamOne = team.get();

        team = Optional.ofNullable(teamRepository.findByName(match.getTeam2Name()).orElseThrow(() ->
                new ResourceNotFound("Team with teamId " + match.getTeam2Name() + " does not found")));
        Team teamTwo = team.get();

        match.setId(sequenceGeneratorService.generateSequence(match.SEQUENCE_NAME)); // id genrator:

        date = findDate();
        match.setDate(date);

        ScoreBoard scoreBoard = new ScoreBoard(match.getId(), match.getTeam1Name(), match.getTeam2Name());
        match.setScoreboardId(match.getId());

        this.match = match;
        this.scoreBoard = scoreBoard;
        this.team1 = teamOne;
        this.team2 = teamTwo;

        startGame();

        updateBattingAndBowlingStats(scoreBoard);
        updateTeamStats(scoreBoard);
        scoreBoardRepository.save(scoreBoard);
        matchRepository.save(match);
        return scoreBoard;
    }
    @Override
    public Match getMatchBYDate(String date) {
        Match match = Optional.of(matchRepository.findTopByDate(date)).orElseThrow(() ->
                new ResourceNotFound("No match found on given date "));
        return match;
    }

    @Override
    public Optional<Match> getMatchByTeamName(String team1Name, String team2Name) {
        return Optional.ofNullable(matchRepository.findTopByTeam1NameAndTeam2Name(team1Name, team2Name).orElseThrow(() ->
                new ResourceNotFound("Match with this team not found")));
    }

    @Override
    public Optional<Match> getMatchByTeamNameAndDate(String team1Name, String team2Name, String date) {
        return Optional.of(matchRepository.findByTeam1NameAndTeam2NameAndDate(team1Name,team2Name,date)).orElseThrow(() ->
                new ResourceNotFound("No match found on this " + date));
    }

    @Override
    public List<Match> getAllMatchByDate(String date) {
        return (List<Match>) matchRepository.findByDate(date);
    }


    private void updateTeamStats(ScoreBoard scoreBoard) {
        Optional<TeamStats> teamStats = teamStatsRepository.findById(team1.getId());
        teamStats.get().increaseNoOfMatchesPlayed();
        if (scoreBoard.getWinner() == team1.getName()) {
            teamStats.get().increaseNoOfMatchesWin();
        } else {
            teamStats.get().increaseNoOfMatchesLost();
        }
        teamStatsRepository.save(teamStats.get());

        teamStats = teamStatsRepository.findById(team2.getId());
        teamStats.get().increaseNoOfMatchesPlayed();
        if(scoreBoard.getWinner() == team2.getName())
        {
            teamStats.get().increaseNoOfMatchesWin();
        } else {
            teamStats.get().increaseNoOfMatchesLost();
        }
        teamStatsRepository.save(teamStats.get());
    }


    private String findDate() {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentDate);
        return dateString;
    }

    public ScoreBoard startGame()
    {
        Team battingTeam;
        Team bowlingTeam;
        String result = getToss(team1,team2);
        if(result == team1.getName())
        {
            battingTeam = team1;
            bowlingTeam = team2;
        }
        else{
            battingTeam =team2;
            bowlingTeam=team1;
        }

        scoreBoard.setBattingTeamName(battingTeam.getName());
        int totalScore = startInning(battingTeam,bowlingTeam,1,10000);
        int secongInningScore = startInning(bowlingTeam,battingTeam,2,totalScore);

        if(totalScore > secongInningScore)
            scoreBoard.setWinner(battingTeam.getName());
        else if(secongInningScore > totalScore)
            scoreBoard.setWinner(bowlingTeam.getName());
        else
            scoreBoard.setWinner("Draw");
        return scoreBoard;
    }

    public String getToss(Team team1, Team team2) {
        double toss = (int) (Math.random() * 2);
        if (toss == 0) {
            return team1.getName();
        } else
            return team2.getName();
    }
    public int startInning(Team team1,Team team2,int inningCount,int totalScore)
    {

        List<BattingStats> team1BattingStats = battingStatsService.initliaseBatting(team1.getPlayerIds(), match.getId(),date);
        List<BowlingStats> team2BowlingStats = bowlingStatsService.initliaseBowling(team2.getPlayerIds(),match.getId() , date);
        Commentry commentry = commentryService.initliaseCommentry(match.getId() , team1.getId());

        int playerOnStrike = 0,playeOnNonStrike = 1,bowlingIndex = 6,total = 0, wicket = 0;
        boolean flag = false;

        for(int over = 0;over < match.getNoOfOvers();over++)
        {
            for(int current = 0 ; current < 6; current++)
            {
                int[] list = ballOutput(playerOnStrike,playeOnNonStrike,total,wicket,over,current,team1BattingStats,team2BowlingStats,bowlingIndex,commentry);
                playerOnStrike = list[0];
                playeOnNonStrike = list[1];
                total = list[2];
                wicket = list[3];
                if(wicket>=10 || totalScore < total){
                    flag = true;
                    break;
                }
            }
            if(flag)
                break;
            bowlingIndex = (bowlingIndex + 1)== 11 ? 6 : bowlingIndex +1;
        }
        scoreboardService.updateScoreboard(scoreBoard , inningCount , total , wicket ,team1BattingStats , team2BowlingStats);
        commentryRepository.save(commentry);
        return total;
    }
    public int[] ballOutput(int playerOnStrike , int playerOnNonStrike , int total , int wicket, int over , int current
                                    , List<BattingStats> team1BattingStats , List<BowlingStats> team2BowlingStats, int bowlingIndex, Commentry commentry)
    {
        team1BattingStats.get(playerOnStrike).increseBallFaced();
        team2BowlingStats.get(bowlingIndex).increaseBallBowled();
        int output = generateRunOnCurrentBall(playerOnStrike);
        Ball ball = new Ball(over+1,current+1,team1BattingStats.get(playerOnStrike).getPlayerName(),
                team2BowlingStats.get(bowlingIndex).getPlayerName(),output,1);
        commentryService.addBall(commentry , ball);
        if(output > 6)
        {
            playerOnStrike = max(playerOnStrike,playerOnNonStrike) + 1;
            team2BowlingStats.get(bowlingIndex).increaseTotalWicket();
            wicket++;
        }
        else
        {
            team1BattingStats.get(playerOnStrike).increaseRunScored(output);
            team2BowlingStats.get(bowlingIndex).increaserunConcede(output);
            if(output%2 ==1 )
            {
                int temp=playerOnStrike;
                playerOnStrike = playerOnNonStrike;
                playerOnNonStrike = temp;
            }
            total+=output;
        }
        if(current == 5)
            return new int[]{playerOnNonStrike,playerOnStrike,total,wicket};
        return new int[]{playerOnStrike, playerOnNonStrike,total,wicket};
    }
    public int generateRunOnCurrentBall(int preindex)
    {

        int[] batsmanScore = new int[]{0,0,6,2,2,4,2,6,2,7,4,6};
        int[] bowlerScore = new int[]{0,7,1,2,6,7,2,1,6,4,7,6};

        int index =  (int)(Math.random()*11);
        if(preindex < 7)
            return batsmanScore[index];
        else
            return bowlerScore[index];
    }

    private void updateBattingAndBowlingStats(ScoreBoard scoreBoard) {
        battingStatsService.updateStats(scoreBoard);
        bowlingStatsService.updateStats(scoreBoard);
    }
}
