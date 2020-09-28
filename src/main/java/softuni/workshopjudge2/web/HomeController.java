package softuni.workshopjudge2.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import softuni.workshopjudge2.service.CommentService;
import softuni.workshopjudge2.service.ExerciseService;
import softuni.workshopjudge2.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {


    private final UserService userService;
    private final CommentService commentService;
    private final ExerciseService exerciseService;

    @Autowired
    public HomeController(UserService userService, CommentService commentService, ExerciseService exerciseService) {
        this.userService = userService;
        this.commentService = commentService;
        this.exerciseService = exerciseService;
    }


    @GetMapping("/")
    public String index(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("user") == null) {
            return "index";
        }

        model.addAttribute("totalUserCount", this.userService.findTotalUsersCount());
        model.addAttribute("avg", this.commentService.getAvgScore());
        model.addAttribute("scoreMap", this.commentService.getScoreMap());
        model.addAttribute("activeEx", this.exerciseService.findAllExNames());
        return "home";
    }
}
