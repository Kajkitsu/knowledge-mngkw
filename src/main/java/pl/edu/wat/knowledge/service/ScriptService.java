package pl.edu.wat.knowledge.service;


import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.repository.ArticleRepository;
import pl.edu.wat.knowledge.repository.AuthorRepository;

@Service
@Slf4j
public class ScriptService {
    private final ArticleRepository articleRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public ScriptService(ArticleRepository articleRepository, AuthorRepository authorRepository) {
        this.articleRepository = articleRepository;
        this.authorRepository = authorRepository;
    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("articleRepository", articleRepository);
            bindings.putMember("authorRepository", authorRepository);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }
    }
}
