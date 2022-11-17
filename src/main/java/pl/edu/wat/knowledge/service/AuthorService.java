package pl.edu.wat.knowledge.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.dto.AuthorRequest;
import pl.edu.wat.knowledge.dto.AuthorResponse;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.exception.EntityNotFound;
import pl.edu.wat.knowledge.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Optional<AuthorResponse> getAuthorById(String id) {
        return authorRepository.findById(id)
                .map(author -> new AuthorResponse(author.getId(), author.getName(), author.getSurname()));
    }

    public AuthorResponse save(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());
        author.setSurname(authorRequest.getSurname());
        author.setPesel(authorRequest.getPesel());
        System.out.println(author);
        author = authorRepository.save(
                author
        );
        System.out.println(author);
        return new AuthorResponse(author.getId(), author.getName(), author.getSurname());
    }

    public List<AuthorResponse> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(author -> new AuthorResponse(author.getId(), author.getName(), author.getSurname()))
                .toList();
    }

    public AuthorResponse update(String id, String name, String surname) throws EntityNotFound {
        Author author = authorRepository.findById(id).orElseThrow(EntityNotFound::new);
        if(StringUtils.isNotBlank(name)) {
            author.setName(name);
        }

        if(StringUtils.isNotBlank(surname)) {
            author.setSurname(surname);
        }

        author = authorRepository.save(author);
        return new AuthorResponse(author.getId(), author.getName(), author.getSurname());
    }
}
