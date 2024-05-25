package pl.edu.wat.knowledge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.edu.wat.knowledge.entity.Article;

@RepositoryRestResource(collectionResourceRel = "articles", path = "articles")
public interface ArticleRepository extends MongoRepository<Article, String> {
}
