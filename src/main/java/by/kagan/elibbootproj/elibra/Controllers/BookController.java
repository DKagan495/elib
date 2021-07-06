package by.kagan.elibbootproj.elibra.Controllers;

import by.kagan.elibbootproj.elibra.DAO.BookDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class BookController {
    private BookDAO bookDAO;
    private HttpSession httpSession;
    @Autowired
    public BookController(BookDAO bookDAO, HttpSession httpSession){
        this.bookDAO = bookDAO;
        this.httpSession = httpSession;
    }
    @GetMapping("/booklist")
    public String showBookList(Model model) throws SQLException {
        model.addAttribute("booklist", bookDAO.showAll());
        return "booklist";
    }
}
