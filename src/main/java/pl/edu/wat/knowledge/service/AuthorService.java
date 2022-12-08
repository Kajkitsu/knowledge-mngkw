package pl.edu.wat.knowledge.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.dto.AuthorRequest;
import pl.edu.wat.knowledge.dto.AuthorResponse;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.exception.EntityNotFound;
import pl.edu.wat.knowledge.mapper.AuthorMapper;
import pl.edu.wat.knowledge.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public Optional<AuthorResponse> getAuthorById(String id) {
        return authorRepository.findById(id)
                .map(authorMapper::map);
    }

    public AuthorResponse save(AuthorRequest authorRequest) {
        Author author = authorMapper.map(authorRequest);
        author = authorRepository.save(
                author
        );
        return authorMapper.map(author);
    }

    public List<AuthorResponse> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::map)
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
        return authorMapper.map(author);
    }
}
