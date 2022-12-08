package pl.edu.wat.knowledge.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.knowledge.dto.AuthorRequest;
import pl.edu.wat.knowledge.dto.AuthorResponse;
import pl.edu.wat.knowledge.entity.Author;

@Service
public class AuthorMapper {



    public Author map(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());
        author.setSurname(authorRequest.getSurname());
        author.setPesel(authorRequest.getPesel());
        fillAuthorRequest(author, authorRequest);
        return author;
    }

    private void fillAuthorRequest(Author author, AuthorRequest authorRequest) {
//        author.setRank(authorRequest.getRank());
        // empty for byte buddy
    }

    public AuthorResponse map(Author author) {
        AuthorResponse authorResponse = new AuthorResponse(author.getId(), author.getName(), author.getSurname());
        fillAuthor(authorResponse, author);
        return authorResponse;
    }

    private void fillAuthor(AuthorResponse authorResponse, Author author) {
        //authorResponse.setRank(author.getRank());
        // empty for byte buddy
    }


}
