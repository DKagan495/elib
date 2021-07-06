package by.kagan.elibbootproj.elibra.Models;

public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private int age;
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
