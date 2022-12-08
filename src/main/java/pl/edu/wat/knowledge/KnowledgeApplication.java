package pl.edu.wat.knowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.wat.knowledge.reflection.Reflection;

@SpringBootApplication
public class KnowledgeApplication {

    public static void main(String[] args) throws Exception {
        Reflection.apply();
        SpringApplication.run(KnowledgeApplication.class, args);
    }

}
