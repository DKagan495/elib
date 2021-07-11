package by.kagan.elibbootproj.elibra.Controllers;

import by.kagan.elibbootproj.elibra.DAO.UserDAO;
import by.kagan.elibbootproj.elibra.Models.User;
import by.kagan.elibbootproj.elibra.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;


@Controller
public class UserController {
    private final UserDAO userDAO;
    private SessionService sessionService = new SessionService();
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
    public String loginAttempt(@ModelAttribute User user/*, RedirectAttributes redirectAttributes*/) throws SQLException {
        if(userDAO.doLog(user)) {
            user.setLogin(true);
            //redirectAttributes.addFlashAttribute("user", user);
            httpSession.setAttribute("id", user.getId());
            System.out.println(httpSession.getAttribute("id") + " it is id");
            httpSession.setAttribute("email", user.getEmail());
            httpSession.setAttribute("password", user.getPassword());
            httpSession.setAttribute("isLogin", user.isLogin());
            SessionService.setCURRENTSESSION(httpSession);
            System.out.println(SessionService.getCURRENTSESSION().getAttribute("email") + "it is session service saved email");
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
        httpSession.setAttribute("id", user.getId());
        System.out.println(httpSession.getAttribute("id") + " it is id");
        httpSession.setAttribute("name", user.getName());
        httpSession.setAttribute("surname", user.getSurname());
        httpSession.setAttribute("isLogin", user.isLogin());
        httpSession.setAttribute("email", user.getEmail());
        httpSession.setAttribute("password", user.getPassword());
        System.out.println(httpSession.getAttribute("name") + " " + httpSession.getAttribute("surname"));
        return "redirect:/mycard";
    }
    @GetMapping("/mycard")
    public String toMyCard(Model model) throws SQLException {
        System.out.println("method is working froo " + (String) httpSession.getAttribute("email"));
        model.addAttribute("user", userDAO.toMyCard((String) httpSession.getAttribute("email"), (String) httpSession.getAttribute("password")));
        return "mycard";
    }
    @GetMapping("/users")
    public String toUserList(Model model) throws SQLException{
        if(httpSession.getAttribute("isLogin")==null || !(boolean) httpSession.getAttribute("isLogin")){
            return "redirect:/login";
        }
        model.addAttribute("userlist", userDAO.showUserList());
        return "userlist";
    }
    @GetMapping("/users/{id}")
    public String toUserCard(@PathVariable int id, Model model) throws SQLException{
        if(httpSession.getAttribute("isLogin")==null || !(boolean) httpSession.getAttribute("isLogin")){
            return "redirect:/login";
        }
        model.addAttribute("user", userDAO.toUserCard(id));
        return "usercard";
    }
    @GetMapping("/sucget")
    public String toSucGet(@ModelAttribute User user) throws SQLException {
        user.setId((int)httpSession.getAttribute("id"));
        //System.out.println(user.getId() + "is uer id");
        userDAO.plusBookUpd(user);
        return "redirect:/mycard";
    }
    @GetMapping("/mybooks")
    public String toMyBooks(Model model) throws SQLException {
        model.addAttribute("books", userDAO.showMyBooks((int) httpSession.getAttribute("id")));
        return "mybooks";
    }
    @GetMapping("/logout")
    public String logOut(@ModelAttribute User user){
        httpSession.invalidate();
        userDAO.doLogOut(user);
        return "redirect:/login";
    }
}
