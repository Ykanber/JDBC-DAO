package yk.jdbc.dao.intro;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import yk.jdbc.dao.intro.dao.AuthorDao;
import yk.jdbc.dao.intro.domain.Author;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"yk.jdbc.dao.intro.dao"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {

    @Autowired
    AuthorDao authorDao;

    @Test
    void testGetAuthor() {
        Author author = authorDao.getById(1L);
        assertThat(author).isNotNull();
    }

    @Test
    void testGetAuthorByName(){
        Author author = authorDao.getByName("Craig","Walls");
        assertThat(author).isNotNull();
    }

    @Test
    void testSaveAuthor(){
        Author author = new Author();
        author.setFirstName("Yavuz");
        author.setLastName("Kanber");
        var authorSaved = authorDao.saveNewAuthor(author);

        assertThat(authorSaved).isNotNull();
    }
    @Test
    void testUpdateAuthor(){
        Author author = new Author();
        author.setFirstName("Yavuz");
        author.setLastName("K");

        Author saved = authorDao.saveNewAuthor(author);
        saved.setLastName("Kanber");
        Author updated = authorDao.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo("Kanber");
    }

    @Test
    void testDeleteAuthor(){
        Author author = new Author();
        author.setFirstName("Yavuz");
        author.setLastName("Kanber");

        Author saved = authorDao.saveNewAuthor(author);

        authorDao.deleteAuthorById(saved.getId());

        Author deleted = authorDao.getById(author.getId());
        assertThat(deleted).isNull();
    }
}













