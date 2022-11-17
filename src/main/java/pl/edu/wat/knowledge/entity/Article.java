package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Article {
    @MongoId
    private String id;
    private String title;
    private Integer score;
    private String authorId;

}
