package yk.jdbc.dao.intro.dao;

import org.springframework.stereotype.Component;
import yk.jdbc.dao.intro.domain.Author;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class AuthorDaoImpl implements AuthorDao {

    private final DataSource source;

    public AuthorDaoImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public Author getById(Long id) {
        Connection connection = null;
        ResultSet resultSet =null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM author WHERE id = ?");
            ps.setLong(1,id);
            resultSet = ps.executeQuery();

            if (resultSet.next()){
                Author author = new Author();
                author.setId(id);
                author.setFirstName(resultSet.getString("first_name"));
                author.setLastName(resultSet.getString("last_name"));
                return author;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                if(resultSet!=null){
                    resultSet.close();
                }
                if(ps != null){
                    ps.close();
                }
                if(connection != null){
                    connection.close();
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return null;
    }
}