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
    public Author getByName(String firstName, String lastName) {
        Connection connection = null;
        ResultSet resultSet =null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM author WHERE first_name = ? and last_name = ?");
            ps.setString(1,firstName);
            ps.setString(2,lastName);
            resultSet = ps.executeQuery();

            if (resultSet.next()){
                return getAuthorFromRS(resultSet);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                closeAll(resultSet,ps,connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return null;
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
                return getAuthorFromRS(resultSet);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                closeAll(resultSet,ps,connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Author saveNewAuthor(Author author) {
        Connection connection = null;
        ResultSet resultSet =null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("Insert INTO author(first_name,last_name) " +
                    "values (?,?)");
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.execute();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");

            if (resultSet.next()){
                long savedId = resultSet.getLong(1);
                return this.getById(savedId);
            }
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                closeAll(resultSet,ps,connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Author updateAuthor(Author author) {

        Connection connection = null;
        ResultSet resultSet =null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("UPDATE author set first_name = ?, last_name = ? WHERE author.id = ?");
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.setLong(3,author.getId());
            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                closeAll(resultSet,ps,connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(long id) {

        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("Delete from author Where id = ?");
            ps.setLong(1, id);
            ps.execute();

        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            try {
                closeAll(null,ps,connection);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    private Author getAuthorFromRS(ResultSet resultSet) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getLong("id"));
        author.setFirstName(resultSet.getString("first_name"));
        author.setLastName(resultSet.getString("last_name"));
        return author;
    }

    private void closeAll(ResultSet resultSet, PreparedStatement ps, Connection connection) throws SQLException {
            if(resultSet!=null){
                resultSet.close();
            }
            if(ps != null){
                ps.close();
            }
            if(connection != null){
                connection.close();
            }
    }
}
