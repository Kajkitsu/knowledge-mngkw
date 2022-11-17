package pl.edu.wat.knowledge.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.knowledge.dto.ArticleRequest;
import pl.edu.wat.knowledge.dto.ArticleResponse;
import pl.edu.wat.knowledge.exception.EntityNotFound;
import pl.edu.wat.knowledge.service.ArticleService;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping()
    public ResponseEntity<List<ArticleResponse>> getAllArticle() {
        List<ArticleResponse> authorOptional = articleService.getAll();
        return new ResponseEntity<>(authorOptional, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createArticle(@RequestBody ArticleRequest authorRequest) {
        try {
            return new ResponseEntity<>(articleService.save(authorRequest).getId(), HttpStatus.CREATED);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
