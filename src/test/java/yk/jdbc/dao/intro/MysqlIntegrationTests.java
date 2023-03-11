package yk.jdbc.dao.intro;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import yk.jdbc.dao.intro.domain.Author;
import yk.jdbc.dao.intro.domain.Book;
import yk.jdbc.dao.intro.repositories.AuthorRepository;
import yk.jdbc.dao.intro.repositories.BookRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MysqlIntegrationTests {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void TestMySql(){
        long countBefore = bookRepository.count();
        assertThat(countBefore).isGreaterThan(0);
    }

    @Test
    void AuthorRepoTest(){
        Author author = authorRepository.save(new Author());
        assertThat(author).isNotNull();
        assertThat(author.getId()).isNotNull();

        Author fetched = authorRepository.getById(author.getId());
        assertThat(fetched).isNotNull();

    }


    @Test
    void BookRepoTest(){
        Book book = bookRepository.save(new Book());
        assertThat(book).isNotNull();
        assertThat(book.getId()).isNotNull();

        Book fetched = bookRepository.getById(book.getId());
        assertThat(fetched).isNotNull();

    }
}

