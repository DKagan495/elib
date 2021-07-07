package by.kagan.elibbootproj.elibra.DAO;

import by.kagan.elibbootproj.elibra.Models.User;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDAO {
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
    public void doReg(User user) throws SQLException {
        System.out.println("What do ypu mean about this&");
        PreparedStatement selectPreparedStatement = connection.prepareStatement("SELECT id FROM users ");
        ResultSet resultSet = selectPreparedStatement.executeQuery();
        while(resultSet.next()){
            ID = resultSet.getInt("id");
        }
        ID++;
        user.setId(ID);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setInt(8, user.getId());
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setInt(5, user.getAge());
        preparedStatement.setInt(6, user.getBooksHave());
        preparedStatement.setInt(7, user.getBooksDone());
        preparedStatement.executeUpdate();
    }
    /*public User toUsersCard(){

    }*/
    public boolean doLog(User user) throws SQLException{
        PreparedStatement preparedStatement = connection.prepareStatement("select*from users");
        List<User> userList = new ArrayList<>();
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            User exampleUser = new User();
            exampleUser.setId(resultSet.getInt("id"));
            ID = exampleUser.getId();
            exampleUser.setName(resultSet.getString("name"));
            exampleUser.setSurname(resultSet.getString("surname"));
            exampleUser.setEmail(resultSet.getString("email"));
            exampleUser.setPassword(resultSet.getString("password"));
            exampleUser.setAge(resultSet.getInt("age"));
            exampleUser.setBooksHave(resultSet.getInt("bookshave"));
            exampleUser.setBooksDone(resultSet.getInt("booksdone"));
            userList.add(exampleUser);
        }
        int counter = 0;
        System.out.println("Method is working! " + ID);
        while(counter<=ID){
            if(userList.get(counter).getEmail().equals(user.getEmail()) && userList.get(counter).getPassword().equals(user.getPassword())){
                user.setLogin(true);
                System.out.println("success");
                return true;
            }
            counter++;
        }
        System.out.println("down");
        return false;
    }
    public User toMyCard(String email, String password) throws SQLException {
        System.out.println("DAOemail + " + email);
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
            ID = user.getId();
            userList.add(user);
        }
        int counter = 0;
        while(counter <= ID){
            if(userList.get(counter).getEmail().equals(email) && userList.get(counter).getPassword().equals(password))
                return userList.get(counter);
            counter++;
        }
            return null;
    }
    public User toUserCard(int id) throws SQLException{
        List<User> userList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setEmail(resultSet.getString("email"));
            user.setAge(resultSet.getInt("age"));
            user.setBooksHave(resultSet.getInt("bookshave"));
            user.setBooksDone(resultSet.getInt("booksdone"));
            userList.add(user);
        }
        return userList.get(id);
    }
    public List<User> showUserList() throws SQLException{
        List<User> userList = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            userList.add(user);
        }
        return userList;
    }
    public void doLogOut(User user){
        user.setLogin(false);
    }
}
