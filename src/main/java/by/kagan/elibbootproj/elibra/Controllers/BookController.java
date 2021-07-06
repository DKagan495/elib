package by.kagan.elibbootproj.elibra.Controllers;

import by.kagan.elibbootproj.elibra.DAO.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

@Controller
public class BookController {
    private BookDAO bookDAO;
    private HttpSession httpSession;
    @Autowired
    public BookController(BookDAO bookDAO, HttpSession httpSession){
        this.bookDAO = bookDAO;
        this.httpSession = httpSession;
    }

}
