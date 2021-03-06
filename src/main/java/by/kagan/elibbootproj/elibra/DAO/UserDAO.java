package by.kagan.elibbootproj.elibra.DAO;

import by.kagan.elibbootproj.elibra.Models.Book;
import by.kagan.elibbootproj.elibra.Models.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDAO {
    private int id = 0;
    private static int ID = 0;
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
    private List<User> userDBList() throws SQLException {
        ID = 0;
        List<User> userList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select*from users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setAge(resultSet.getInt("age"));
            user.setBooksHave(resultSet.getInt("bookshave"));
            user.setBooksDone(resultSet.getInt("booksdone"));
            user.setOnlineStatus(resultSet.getBoolean("online_status"));
            userList.add(user);
        }
        userList.sort((o1, o2) -> o1.getId() - o2.getId());
        ID = userList.get(userList.size()-1).getId();
        return userList;
    }
    public void doReg(User user) throws SQLException {
        System.out.println("what&");
        userDBList();
        user.setId(ID+1);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(8, user.getId());
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setInt(5, user.getAge());
        preparedStatement.setInt(6, user.getBooksHave());
        preparedStatement.setInt(7, user.getBooksDone());
        preparedStatement.setBoolean(9, true);
        preparedStatement.executeUpdate();
    }
    /*public User toUsersCard(){

    }*/
    public boolean doLog(User user) throws SQLException{
        int counter = 0;
        while(counter < userDBList().size()){
            if(userDBList().get(counter).getEmail().equals(user.getEmail()) && userDBList().get(counter).getPassword().equals(user.getPassword())){
                user.setId(userDBList().get(counter).getId());
                user.setLogin(true);
                System.out.println("success");
                PreparedStatement preparedStatement = connection.prepareStatement("update users set online_status = true where email = ? and password = ?");
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.executeUpdate();
                return true;
            }
            counter++;
        }
        System.out.println("????????????-???? ???????????????? ??????????");
        //user.setLogin(false);
        return false;
    }
    public User toMyCard(String email, String password) throws SQLException {
        System.out.println("DAOemail + " + email);
        int counter = 0;
        while(counter <= ID){
            if(userDBList().get(counter).getEmail().equals(email) && userDBList().get(counter).getPassword().equals(password))
                return userDBList().get(counter);
            counter++;
        }
            return null;
    }
    public User toUserCard(int id) throws SQLException{
        return userDBList().stream().filter(n->n.getId() == id).findAny().orElse(null);
    }
    public List<User> showUserList() throws SQLException{
        return userDBList();
    }
    public void updateUser(User user) throws SQLException{
        System.out.println(user.getId() + " edited user name");
        PreparedStatement preparedStatement = connection.prepareStatement("update users set name = ?, surname = ?, age = ? where id = ?");
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setInt(3, user.getAge());
        preparedStatement.setInt(4, user.getId());
        preparedStatement.executeUpdate();
        System.out.println("final o4ka");
    }
    public void deleteUser(int id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("delete from users where id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatement1 = connection.prepareStatement("delete from b2udone where user_id = ?");
        preparedStatement1.setInt(1, id);
        preparedStatement1.executeUpdate();
        PreparedStatement preparedStatement2 = connection.prepareStatement("delete from bookstousers where user_id = ?");
        preparedStatement2.setInt(1, id);
        preparedStatement2.executeUpdate();
    }
    public void plusBookUpd(User user) throws SQLException{
        PreparedStatement pstmt = connection.prepareStatement("select bookshave from users where id = ?");
        pstmt.setInt(1, user.getId());
        ResultSet resultSet = pstmt.executeQuery();
        if(resultSet.next()) {
            user.setBooksHave(resultSet.getInt("bookshave") + 1);
        }
        //System.out.println(user.getBooksHave + " have books now");
        PreparedStatement preparedStatement = connection.prepareStatement("update users set bookshave = ? where id = ?");
        PreparedStatement preparedStatement1 = connection.prepareStatement("update bookstousers set user_id = ? where user_id is null");
        preparedStatement.setInt(1, user.getBooksHave());
        preparedStatement.setInt(2, user.getId());
        preparedStatement1.setInt(1, user.getId());
        preparedStatement.executeUpdate();
        preparedStatement1.executeUpdate();
    }
    public void bookToCompleteUpd(User user) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("update users set bookshave = bookshave - 1 where id = ?");
        preparedStatement.setInt(1, user.getId());
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatement1 = connection.prepareStatement("update users set booksdone = booksdone + 1 where id = ?");
        preparedStatement1.setInt(1, user.getId());
        preparedStatement1.executeUpdate();
    }
    public List<Book> showMyBooks(int id) throws SQLException {
        List<Book> myBooksList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select book_id, book_name, book_author from bookstousers where user_id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Book book = new Book();
            book.setId(resultSet.getInt("book_id"));
            book.setName(resultSet.getString("book_name"));
            book.setAuthor(resultSet.getString("book_author"));
            myBooksList.add(book);
        }
        return myBooksList;
    }
    public List<Book> showCompletedBooks(int id) throws SQLException {
        List<Book> myBooksList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select book_id, book_name, book_author from b2udone where user_id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            Book book = new Book();
            book.setId(resultSet.getInt("book_id"));
            book.setName(resultSet.getString("book_name"));
            book.setAuthor(resultSet.getString("book_author"));
            myBooksList.add(book);
        }
        return myBooksList;
    }
    public boolean isOnline(int id) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("select online_status from users where id = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next())
            return resultSet.getBoolean("online_status");
        return false;
    }
    public void doLogOut(User user) throws SQLException{
        System.out.println(user.isLogin() + "is thius chmous loggined&");
        PreparedStatement preparedStatement = connection.prepareStatement("update users set online_status = false where id = ?");
        preparedStatement.setInt(1, user.getId());
        preparedStatement.executeUpdate();
        user.setLogin(false);
    }
}
