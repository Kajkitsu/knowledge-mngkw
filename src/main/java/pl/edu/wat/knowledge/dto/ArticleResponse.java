package pl.edu.wat.knowledge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.edu.wat.knowledge.entity.Author;

@Data
@AllArgsConstructor
public class ArticleResponse {
    private String id;
    private String title;
    private Integer score;
    private AuthorResponse author;
}
