package pl.edu.wat.knowledge.dto;

import lombok.Data;

@Data
public class AuthorRequest {
    private String surname;
    private String name;
    private String pesel;
}
