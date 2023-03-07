package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.*;
import com.example.cricketapplicationdemo.exceptionHandler.ResourceNotFound;
import com.example.cricketapplicationdemo.helper.Ball;
import com.example.cricketapplicationdemo.repository.*;
import com.example.cricketapplicationdemo.service.interfaces.MatchService;
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
    private PlayerRepository playerRepository;
    @Autowired
    private BattingStatsRepository battingStatsRepository;
    @Autowired
    private BowlingStatsRepository bowlingStatsRepository;
    @Autowired
    private PlayerStatsRepository playerStatsRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Autowired
    TeamStatsRepository teamStatsRepository;
    @Autowired
    private CommentryRepository commentryRepository;

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

        match.setId(sequenceGeneratorService.generateSequence(match.SEQUENCE_NAME));

        date = findDate();
        match.setDate(date);

        ScoreBoard scoreBoard = new ScoreBoard(match.getId(), match.getTeam1Name(), match.getTeam2Name());
        match.setScoreboardId(match.getId());

        this.match = match;
        this.scoreBoard = scoreBoard;
        this.team1 = teamOne;
        this.team2 = teamTwo;

        startGame();

        updateScorecard(scoreBoard);
        updateTeamStats(scoreBoard);
        scoreBoardRepository.save(scoreBoard);
        matchRepository.save(match);
        return scoreBoard;
    }

    private void updateTeamStats(ScoreBoard scoreBoard) {
        Optional<TeamStats> teamStats = teamStatsRepository.findById(team1.getId());
        System.out.println(teamStats.get().getTeamId());
        if(scoreBoard.getWinner() == team1.getName())
        {
            teamStats.get().increaseNoOfMatchesPlayed();
            teamStats.get().increaseNoOfMatchesWin();
        }
        else
        {
            teamStats.get().increaseNoOfMatchesPlayed();
            teamStats.get().increaseNoOfMatchesLost();
        }
        teamStatsRepository.save(teamStats.get());
        teamStats = teamStatsRepository.findById(team2.getId());

        if(scoreBoard.getWinner() == team2.getName())
        {
            teamStats.get().increaseNoOfMatchesPlayed();
            teamStats.get().increaseNoOfMatchesWin();
        }
        else
        {
            teamStats.get().increaseNoOfMatchesPlayed();
            teamStats.get().increaseNoOfMatchesLost();
        }
        teamStatsRepository.save(teamStats.get());
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

    private String findDate() {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentDate);
        //ystem.out.println("Current date as string: " + dateString);
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
        int newScore = startInning(bowlingTeam,battingTeam,2,totalScore);

        if(totalScore > newScore)
            scoreBoard.setWinner(battingTeam.getName());
        else
            scoreBoard.setWinner(bowlingTeam.getName());
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
        List<Integer> battingList = team1.getPlayerIds();
        List<Integer> bowlingList = team2.getPlayerIds();

        int battingIndex = 0;
        int total = 0;
        int playerOnStrike = 0,playeOnNonStrike = 1;
        int bowlingIndex = 6;

        List<BattingStats> team1BattingStats = initliaseBatting(battingList);
        List<BowlingStats> team2BowlingStats = initliaseBowling(bowlingList);
        Commentry commentry = new Commentry();
        commentry.setMatchId(match.getId());
        commentry.setTeamId(team1.getId());
        commentry.setId(sequenceGeneratorService.generateSequence(commentry.SEQUENCE_NAME));

        int noOfOvers = match.getNoOfOvers();
        int wicket = 0;
        boolean flag = false;

        for(int over = 0;over < noOfOvers;over++)
        {
            for(int current = 0 ; current < 6; current++)
            {
                team1BattingStats.get(playerOnStrike).increseBallFaced();
                team2BowlingStats.get(bowlingIndex).increaseBallBowled();
                int output = generateRunOnCurrentBall(battingIndex);
                Ball ball = new Ball(over+1,current+1,team1BattingStats.get(playerOnStrike).getPlayerName(),
                        team2BowlingStats.get(bowlingIndex).getPlayerName(),output,1);
                commentry.addResult(ball);
                if(output > 6)
                {
                    playerOnStrike = max(playerOnStrike,playeOnNonStrike) + 1;
                    wicket++;
                    if(wicket>=10)
                    {
                        flag = true;
                        break;
                    }
                    team2BowlingStats.get(bowlingIndex).increaseTotalWicket();
                }
                else
                {
                    team1BattingStats.get(playerOnStrike).increaseRunScored(output);
                    team2BowlingStats.get(bowlingIndex).increaserunConcede(output);
                    if(output%2 ==1)
                    {
                        int temp=playerOnStrike;
                        playerOnStrike = playeOnNonStrike;
                        playeOnNonStrike = temp;
                    }

                    totalScore -= output;
                    total+=output;
                    if(totalScore < 0)
                    {
                        flag = true;
                        break;
                    }
                }
            }
            if(flag)
                break;

            int temp=playerOnStrike;
            playerOnStrike = playeOnNonStrike;
            playeOnNonStrike = temp;

            bowlingIndex = (bowlingIndex + 1);
            if(bowlingIndex == 11)
                bowlingIndex =6;
        }

        if(inningCount == 1)
        {
            scoreBoard.setScoreOfFirstTeam(total);
            scoreBoard.setWicketOfFirstTeam(wicket);
            scoreBoard.setTeam1BattingStats(team1BattingStats);
            scoreBoard.setTeam2BowlingStats(team2BowlingStats);
        }
        else
        {
            scoreBoard.setScoreOfSecondTeam(total);
            scoreBoard.setWicketOfSecondTeam(wicket);
            scoreBoard.setTeam2BattingStats(team1BattingStats);
            scoreBoard.setTeam1BowlingStats(team2BowlingStats);
        }
        commentryRepository.save(commentry);
        return total;

    }

    private List<BowlingStats> initliaseBowling(List<Integer> bowlingList) {
        List<BowlingStats> list = new ArrayList<>();
        for(int i = 0 ;i<bowlingList.size();i++)
        {
            BowlingStats bowlingStats = new BowlingStats();
            int index = bowlingList.get(i);

            Optional<Player> player = playerRepository.findById(index);

            bowlingStats.setId(sequenceGeneratorService.generateSequence(bowlingStats.SEQUENCE_NAME));
            bowlingStats.setPlayerId(index);
            bowlingStats.setMatchId(match.getId());
            bowlingStats.setPlayerName(player.get().getName());
            bowlingStats.setDate(date);
            list.add(bowlingStats);
        }
        return list;
    }

    private List<BattingStats> initliaseBatting(List<Integer> battingList) {

        List<BattingStats> list = new ArrayList<>();
        for(int i = 0 ;i<battingList.size();i++)
        {
            BattingStats battingStats = new BattingStats();
            int index = battingList.get(i);

            Optional<Player> player = playerRepository.findById(index);

            battingStats.setId(sequenceGeneratorService.generateSequence(battingStats.SEQUENCE_NAME));
            battingStats.setPlayerId(index);
            battingStats.setMatchId(match.getId());
            battingStats.setPlayerName(player.get().getName());
            battingStats.setDate(date);
            list.add(battingStats);

        }
        return list;
    }
    public static int generateRunOnCurrentBall(int preindex)
    {

        int[] batsmanScore = new int[]{0,0,6,2,2,4,2,6,2,7,4,6};
        int[] bowlerScore = new int[]{0,7,1,2,6,7,2,1,6,4,7,6};

        int index =  (int)(Math.random()*11);
        if(preindex < 7)
            return batsmanScore[index];
        else
            return bowlerScore[index];
    }

    private void updateScorecard(ScoreBoard scoreBoard) {

        List<BattingStats> team1BattingStats = scoreBoard.getTeam1BattingStats();
        List<BattingStats> team2BattingStats = scoreBoard.getTeam2BattingStats();
        List<BowlingStats> team1BowlingStats = scoreBoard.getTeam1BowlingStats();
        List<BowlingStats> team2BowlingStats = scoreBoard.getTeam2BowlingStats();

        Optional<Player> player;
        Optional<PlayerStats> playerStats;

        for (int i = 0; i < team1BattingStats.size(); i++) {
            BattingStats battingStats = team1BattingStats.get(i);
            if (battingStats.getBallFaced() > 0) {
                updateBattingStatsOfPlayer(battingStats);
            }
            battingStats = team2BattingStats.get(i);
            if (battingStats.getBallFaced() > 0) {
                updateBattingStatsOfPlayer(battingStats);
            }

            BowlingStats bowlingStats = team1BowlingStats.get(i);
            if (bowlingStats.getBallsBowled() > 0) {
                updateBowlingStatsOfPlayer(bowlingStats);
            }

            bowlingStats = team2BowlingStats.get(i);
            if (bowlingStats.getBallsBowled() > 0) {
                updateBowlingStatsOfPlayer(bowlingStats);
            }
        }
    }
    private void updateBattingStatsOfPlayer(BattingStats battingStats)
    {
        Optional<Player> player;
        Optional<PlayerStats> playerStats;
        player = playerRepository.findById(battingStats.getPlayerId());
        playerRepository.save(player.get());

        playerStats = playerStatsRepository.findById(battingStats.getPlayerId());
        playerStats.get().updateStats(battingStats);
        playerStatsRepository.save(playerStats.get());

        battingStatsRepository.save(battingStats);
    }
    private void updateBowlingStatsOfPlayer(BowlingStats bowlingStats)
    {
        Optional<Player> player;
        Optional<PlayerStats> playerStats;

        player = playerRepository.findById(bowlingStats.getPlayerId());
        playerRepository.save(player.get());

        playerStats = playerStatsRepository.findById(bowlingStats.getPlayerId());
        playerStats.get().updateBowlingStats(bowlingStats);
        playerStatsRepository.save(playerStats.get());

        bowlingStatsRepository.save(bowlingStats);
    }
}
