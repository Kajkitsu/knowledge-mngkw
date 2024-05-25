package pl.edu.wat.knowledge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KnowledgeApplication {

    public static void main(String[] args) throws Exception {
//        Reflection.apply();
        SpringApplication.run(KnowledgeApplication.class, args);
    }

}
