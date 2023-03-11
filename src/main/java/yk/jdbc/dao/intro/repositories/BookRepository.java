package yk.jdbc.dao.intro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yk.jdbc.dao.intro.domain.Book;

public interface BookRepository extends JpaRepository<Book,Long> {

}
