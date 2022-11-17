package pl.edu.wat.knowledge.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.knowledge.dto.AuthorRequest;
import pl.edu.wat.knowledge.dto.AuthorResponse;
import pl.edu.wat.knowledge.entity.Author;
import pl.edu.wat.knowledge.exception.EntityNotFound;
import pl.edu.wat.knowledge.service.AuthorService;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public ResponseEntity<List<AuthorResponse>> getAllAuthor() {
        List<AuthorResponse> authorOptional = authorService.getAll();
        return new ResponseEntity<>(authorOptional, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorResponse> getAuthorByIdVar(@PathVariable String id) {
        Optional<AuthorResponse> authorOptional = authorService.getAuthorById(id);
        if (authorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(authorOptional.get(), HttpStatus.OK);
    }

    @GetMapping("{id}/surname")
    public ResponseEntity<String> getAuthorSurnameById(@PathVariable String id) {
        Optional<AuthorResponse> authorOptional = authorService.getAuthorById(id);
        if (authorOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(authorOptional.get().getSurname(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createAuthor(@RequestBody AuthorRequest authorRequest) {
        return new ResponseEntity<>(authorService.save(authorRequest).getId(), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable String id, @RequestParam(required = false) String name, @RequestParam(required = false) String surname) {
        try {
            return new ResponseEntity<>(authorService.update(id, name, surname), HttpStatus.OK);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }
}
