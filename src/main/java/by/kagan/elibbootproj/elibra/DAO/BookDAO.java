package by.kagan.elibbootproj.elibra.DAO;

import by.kagan.elibbootproj.elibra.Models.Book;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookDAO {
    private static final String URL = "jdbc:postgresql://localhost:1605/elibdb";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "daka16052002";
    private static Connection connection;
    static {
        try{
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException classNotFoundException)
        {
            classNotFoundException.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public List<Book> showAll() throws SQLException{
        List<Book> bookList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from books");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Book book = new Book();
            book.setId(resultSet.getInt("id"));
            book.setName(resultSet.getString("name"));
            book.setAuthor(resultSet.getString("author"));
            book.setNumberOfPages(resultSet.getInt("numberofpages"));
            book.setYear(resultSet.getInt("year"));
            bookList.add(book);
        }
        return bookList;
    }
}
