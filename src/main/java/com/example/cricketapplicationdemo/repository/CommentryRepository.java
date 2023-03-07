package com.example.cricketapplicationdemo.repository;

import com.example.cricketapplicationdemo.entity.Commentry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentryRepository extends MongoRepository<Commentry,Integer> {
}
