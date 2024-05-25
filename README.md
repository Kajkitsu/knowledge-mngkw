HATEOAS, czyli Hypermedia as the Engine of Application State, to koncepcja w architekturze RESTful API, która sugeruje, że odpowiedzi z serwera powinny zawierać nie tylko dane, ale także informacje o tym, jak te dane można manipulować.

W praktyce, HATEOAS polega na dołączaniu do odpowiedzi linków (href), które wskazują na możliwe następne akcje, które klient może podjąć. Te linki są często zwracane w formacie JSON jako obiekt "_links", który zawiera różne relacje i odpowiadające im adresy URL.

Na przykład, odpowiedź z serwera może wyglądać tak:

```json
{
  "surname": "Waszkowiak",
  "name": "Norbert",
  "pesel": "97541276378",
  "_links": {
    "self": {
      "href": "http://localhost:8080/api/authors/665215f08641905609b1ad39"
    },
    "author": {
      "href": "http://localhost:8080/api/authors/665215f08641905609b1ad39"
    },
    "articles": {
      "href": "http://localhost:8080/api/authors/665215f08641905609b1ad39/articles"
    }
  }
}
```

W tym przypadku, klient otrzymuje nie tylko informacje o użytkowniku (id i name), ale także linki do zasobów związanych z tym użytkownikiem: link do samego siebie (self) i link do listy przyjaciół użytkownika (friends).

HATEOAS jest jednym z kluczowych elementów architektury REST i pomaga w tworzeniu bardziej elastycznych i łatwiejszych do utrzymania API.