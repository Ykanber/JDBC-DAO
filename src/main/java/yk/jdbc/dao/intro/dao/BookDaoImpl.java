package yk.jdbc.dao.intro.dao;

import org.springframework.stereotype.Component;
import yk.jdbc.dao.intro.domain.Book;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class BookDaoImpl implements BookDao {

    DataSource source;

    public BookDaoImpl(DataSource source) {
        this.source = source;
    }

    @Override
    public Book getByTitle(String title) {

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("SELECT * FROM book where title = ?");
            ps.setString(1,title);
            resultSet = ps.executeQuery();

            if(resultSet.next()){
                return getBookFromRS(resultSet);
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
    public Book saveNewBook(Book book) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("Insert INTO book (title,isbn,publisher,author_id) " +
                    "values (?,?,?,?)");
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getIsbn());
            ps.setString(3, book.getPublisher());
            ps.setLong(4, book.getAuthorId());
            ps.execute();

            if (this.getByTitle(book.getTitle()) != null){
                return this.getByTitle(book.getTitle());
            }
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
        return null;
    }

    @Override
    public Book updateBook(Book book) {
        Connection connection = null;
        ResultSet resultSet =null;
        PreparedStatement ps = null;
        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("UPDATE book set isbn = ?," +
                    "publisher = ?,author_id = ? WHERE book.title = ?");
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getPublisher());
            ps.setLong(3,   book.getAuthorId());
            ps.setString(4,book.getTitle());
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

        return this.getByTitle(book.getTitle());
    }

    @Override
    public void deleteBookByTitle(String title) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = source.getConnection();
            ps = connection.prepareStatement("Delete from book Where title = ?");
            ps.setString(1, title);
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

    private Book getBookFromRS(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setIsbn(resultSet.getString("isbn"));
        book.setTitle(resultSet.getString("title"));
        book.setPublisher(resultSet.getString("publisher"));
        book.setAuthorId(resultSet.getLong("author_id"));
        return book;
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
