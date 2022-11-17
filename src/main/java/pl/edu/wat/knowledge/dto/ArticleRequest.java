package pl.edu.wat.knowledge.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.edu.wat.knowledge.entity.Author;

@Data
public class ArticleRequest {
    private String title;
    private String authorId;
}
