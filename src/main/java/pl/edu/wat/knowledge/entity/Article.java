package pl.edu.wat.knowledge.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
public class Article {
    @MongoId
    private String id;
    private String title;
    private Integer score;
    @DBRef
    private List<Author> authors;
}
