package yk.jdbc.dao.intro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import yk.jdbc.dao.intro.domain.Author;

public interface AuthorRepository extends JpaRepository <Author,Long>{
}
