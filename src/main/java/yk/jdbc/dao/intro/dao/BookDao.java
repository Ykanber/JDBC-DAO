package yk.jdbc.dao.intro.dao;

import yk.jdbc.dao.intro.domain.Book;

public interface BookDao {

    Book getByTitle(String title);
    Book saveNewBook(Book book);
    Book updateBook(Book book);
    void deleteBookByTitle(String title);
}
