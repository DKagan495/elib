package by.kagan.elibbootproj.elibra.Controllers;

import by.kagan.elibbootproj.elibra.DAO.UserDAO;
import by.kagan.elibbootproj.elibra.Models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;


@Controller
public class UserController {
    private final UserDAO userDAO;
    private HttpSession httpSession;
    @Autowired
    public UserController(UserDAO userDAO, HttpSession httpSession) {
        this.httpSession = httpSession;
        this.userDAO = userDAO;
    }

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("user", new User());
        if(httpSession.getAttribute("isLogin")==null || !(boolean) httpSession.getAttribute("isLogin")){
            return "loginpage";
        }
        return "redirect:/mycard";
    }
    @PostMapping("/mycard")
    public String loginAttempt(@ModelAttribute User user) throws SQLException {
        if(userDAO.doLog(user)) {
            user.setLogin(true);
            httpSession.setAttribute("email", user.getEmail());
            httpSession.setAttribute("password", user.getPassword());
            httpSession.setAttribute("isLogin", user.isLogin());
            return "redirect:/mycard";
        }
        else
            return "redirect:/login";
    }
    @GetMapping("/reg")
    public String toRegForm(Model model){
        if(httpSession.getAttribute("isLogin")==null || !(boolean) httpSession.getAttribute("isLogin")) {
            model.addAttribute("user", new User());
            return "regform";
        }
        else
            return "redirect:/mycard";
    }
    @PostMapping("/reg")
    public String regAttempt(@ModelAttribute User user) throws SQLException{
        userDAO.doReg(user);
        user.setLogin(true);
        httpSession.setAttribute("name", user.getName());
        httpSession.setAttribute("surname", user.getSurname());
        httpSession.setAttribute("isLogin", user.isLogin());
        httpSession.setAttribute("email", user.getEmail());
        httpSession.setAttribute("password", user.getPassword());
        System.out.println(httpSession.getAttribute("name") + " " + httpSession.getAttribute("surname"));
        return "redirect:/mycard";
    }
    @GetMapping("/mycard")
    public String toUsersCard(Model model) throws SQLException {
        System.out.println("method is working froo " + (String) httpSession.getAttribute("email"));
        model.addAttribute("user", userDAO.toMyCard((String) httpSession.getAttribute("email"), (String) httpSession.getAttribute("password")));
        return "mycard";
    }
    @GetMapping("/users/{id}")
    public String toUserCard(@PathVariable int id, Model model){
        return "usercard";
    }
    @GetMapping("/logout")
    public String logOut(@ModelAttribute User user){
        httpSession.invalidate();
        userDAO.doLogOut(user);
        return "redirect:/login";
    }
}
