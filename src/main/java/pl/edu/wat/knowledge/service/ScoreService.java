package pl.edu.wat.knowledge.service;

import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.entity.Article;

import java.util.Random;

@Service
public class ScoreService {
    public Integer getScore(Article article) {
        return new Random().nextInt(10); //TODO
    }
}
