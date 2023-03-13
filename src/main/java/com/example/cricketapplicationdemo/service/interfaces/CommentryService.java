package com.example.cricketapplicationdemo.service.interfaces;

import com.example.cricketapplicationdemo.entity.Commentry;
import com.example.cricketapplicationdemo.helper.Ball;

public interface CommentryService {
    Commentry initliaseCommentry(int matchId, int teamId);

    void addBall(Commentry commentry, Ball ball);
}
