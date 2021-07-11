package by.kagan.elibbootproj.elibra.Controllers;

import by.kagan.elibbootproj.elibra.DAO.BookDAO;
import by.kagan.elibbootproj.elibra.Models.Book;
import by.kagan.elibbootproj.elibra.Models.User;
import by.kagan.elibbootproj.elibra.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Map;

@Controller
public class BookController {
    private BookDAO bookDAO;
    private HttpSession httpSession;
    private boolean isUserLoggedIn = false;
    @Autowired
    public BookController(BookDAO bookDAO, HttpSession httpSession){
        this.bookDAO = bookDAO;
        this.httpSession = httpSession;
    }
    @GetMapping("/books")
    public String showBookList(Model model, @ModelAttribute User user) throws SQLException {
       /* if(!user.isLogin())
            return "redirect:/login";*/
        model.addAttribute("booklist", bookDAO.showAll());
        System.out.println("sesAttr=" + httpSession.getAttribute("name"));
        return "booklist";
    }
    @GetMapping("/books/{id}")
    public String showBook(@PathVariable int id, Model model) throws SQLException {
        model.addAttribute("book", bookDAO.showBook(id-1));
        SessionService sessionService = new SessionService();
        model.addAttribute("isTaken", bookDAO.isUserTaked((Integer) sessionService.getCURRENTSESSION().getAttribute("id"), id));
        System.out.println("This book is taken me, yes? -- " + bookDAO.isUserTaked((Integer) sessionService.getCURRENTSESSION().getAttribute("id"), id));
        return "book";
    }
    @GetMapping("/books/{id}/get")
    public String getBook(@PathVariable int id, @ModelAttribute Book book, Model model) throws SQLException {
        model.addAttribute("book", bookDAO.showBook(id-1));
        System.out.println(book.getId() + " this is the book id");
        bookDAO.addBookToUser(id);
        return "redirect:/sucget";
    }
}
