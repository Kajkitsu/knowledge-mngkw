package pl.edu.wat.knowledge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.dto.ArticleRequest;
import pl.edu.wat.knowledge.dto.ArticleResponse;
import pl.edu.wat.knowledge.dto.AuthorResponse;
import pl.edu.wat.knowledge.entity.Article;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.exception.EntityNotFound;
import pl.edu.wat.knowledge.repository.ArticleRepository;
import pl.edu.wat.knowledge.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ScoreService scoreService;
    private final AuthorRepository authorRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository, ScoreService scoreService, AuthorRepository authorRepository) {
        this.articleRepository = articleRepository;
        this.scoreService = scoreService;
        this.authorRepository = authorRepository;
    }

    public ArticleResponse getArticleById(String id) throws EntityNotFound {
        Article article = articleRepository.findById(id).orElseThrow(EntityNotFound::new);

        Author author = authorRepository.findById(article.getAuthorId()).orElseThrow(EntityNotFound::new);

        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getScore(),
                new AuthorResponse(author.getId(), author.getName(), author.getSurname()));
    }

    public ArticleResponse save(ArticleRequest articleRequest) throws EntityNotFound {
        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setScore(scoreService.getScore(article));

        Author author = authorRepository.findById(articleRequest.getAuthorId())
                .orElseThrow(EntityNotFound::new);

        article.setAuthorId(author.getId());
        System.out.println(article);
        article = articleRepository.save(
                article
        );
        System.out.println(article);
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getScore(),
                new AuthorResponse(author.getId(), author.getName(), author.getSurname()));
    }

    public List<ArticleResponse> getAll() {

        return articleRepository.findAll()
                .stream()
                .map(this::toArticleResponse)
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<ArticleResponse> toArticleResponse(Article article) {
        try {
            Author author = authorRepository.findById(article.getAuthorId()).orElseThrow(EntityNotFound::new);
            return Optional.of(
                    new ArticleResponse(article.getId(), article.getTitle(), article.getScore(), new AuthorResponse(author.getId(), author.getName(), author.getSurname()))
            );
        } catch (EntityNotFound e) {
            return Optional.empty();
        }
    }
}
