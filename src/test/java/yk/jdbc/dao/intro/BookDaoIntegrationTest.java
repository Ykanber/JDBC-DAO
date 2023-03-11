package yk.jdbc.dao.intro;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import yk.jdbc.dao.intro.dao.BookDao;
import yk.jdbc.dao.intro.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"yk.jdbc.dao.intro.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {

    @Autowired
    BookDao bookDao;


    @Test
    void testGetBookByTitle(){

        Book book = bookDao.getByTitle("Spring in Action, 5th Edition");
        assertThat(book).isNotNull();
    }

    @Test
    void testSaveNewBook(){
        Book book = new Book();
        book.setTitle("Save test book");
        book.setIsbn("1234");
        book.setPublisher("me");
        book.setAuthorId(1l);

        Book saved = bookDao.saveNewBook(book);
        assertThat(saved).isNotNull();
    }

    @Test
    void updateNewBook(){
        Book book = new Book();
        book.setTitle("update test book");
        book.setIsbn("1234");
        book.setPublisher("me");
        book.setAuthorId(1l);

        Book saved = bookDao.saveNewBook(book);
        saved.setIsbn("4321");
        Book updated = bookDao.updateBook(saved);
        assertThat(updated.getIsbn()).isEqualTo("4321");

    }

    @Test
    void deleteBook(){
        Book book = new Book();
        book.setTitle("Delete test book");
        book.setIsbn("1234");
        book.setPublisher("me");
        book.setAuthorId(1l);

        Book saved = bookDao.saveNewBook(book);
        bookDao.deleteBookByTitle(book.getTitle());

        Book deleted = bookDao.getByTitle(saved.getTitle());
        assertThat(deleted).isNull();
    }
}




























