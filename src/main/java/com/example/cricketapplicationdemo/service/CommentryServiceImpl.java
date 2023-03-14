package com.example.cricketapplicationdemo.service;

import com.example.cricketapplicationdemo.entity.Commentry;
import com.example.cricketapplicationdemo.helper.Ball;
import com.example.cricketapplicationdemo.repository.CommentryRepository;
import com.example.cricketapplicationdemo.service.interfaces.CommentryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommentryServiceImpl implements CommentryService {
    @Autowired
    private CommentryRepository commentryRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;
    @Override
    public Commentry initliaseCommentry(int matchId, int teamId) {
        Commentry commentry = Commentry.builder()
                .matchId(matchId)
                .teamId(teamId)
                .id(sequenceGeneratorService.generateSequence(Commentry.SEQUENCE_NAME))
                .result(new ArrayList<>())
                .build();
        return commentry;
    }

    @Override
    public void addBall(Commentry commentry, Ball ball) {
        commentry.getResult().add(ball);
    }
}
