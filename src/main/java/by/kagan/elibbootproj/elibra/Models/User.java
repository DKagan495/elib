package by.kagan.elibbootproj.elibra.Models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class User {
    private int id;
    @NotEmpty(message = "Field name is empty")
    @Size(min = 2, max = 30, message = "Invalid name, see text below the form")
    private String name;
    @NotEmpty(message = "Field surname is empty")
    @Size(min = 2, max = 36, message = "Invalid surname, see text below the form")
    private String surname;
    @NotEmpty(message = "Field email is empty")
    @Email(message = "Invalid email, see text below the form")
    private String email;
    @NotEmpty(message = "Enter the password")
    @Size(min = 8, max = 20, message = "Invalid password, see text below the form")
    private String password;
    @Min(value = 16, message = "Age less than 16")
    private int age;
    private boolean onlineStatus = false;
    private int booksDone = 0;
    private int booksHave = 0;
    private boolean login = false;
    public User() {
    }

    public User(int id, String name, String surname, int age) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getBooksDone() {
        return booksDone;
    }

    public void setBooksDone(int booksDone) {
        this.booksDone = booksDone;
    }

    public int getBooksHave() {
        return booksHave;
    }

    public void setBooksHave(int booksHave) {
        this.booksHave = booksHave;
    }
}
