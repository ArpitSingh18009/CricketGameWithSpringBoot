package com.example.cricketapplicationdemo.repository;

import com.example.cricketapplicationdemo.entity.Match;
import com.example.cricketapplicationdemo.entity.Team;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends MongoRepository<Match,Integer> {

    Match findByDate(String date);

    Optional<Match> findByTeam1NameAndTeam2Name(String team1Name, String team2Name);

    Optional<Match> findTopByTeam1NameAndTeam2Name(String team1Name, String team2Name);

    Optional<Match> findByTeam1NameAndTeam2NameAndDate(String team1Name, String team2Name, String date);

    Match findTopByDate(String date);
}
