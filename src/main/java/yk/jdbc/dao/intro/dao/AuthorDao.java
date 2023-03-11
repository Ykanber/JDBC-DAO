package yk.jdbc.dao.intro.dao;

import yk.jdbc.dao.intro.domain.Author;

public interface AuthorDao {

    Author getById(Long id);
    Author getByName(String firstName,String lastName);

    Author saveNewAuthor(Author author);

    Author updateAuthor(Author saved);

    void deleteAuthorById(long id);
}
