package softuni.workshopjudge2.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.workshopjudge2.model.binding.CommentAddBindingModel;
import softuni.workshopjudge2.model.binding.HomeworkAddBidingModel;
import softuni.workshopjudge2.model.service.CommentServiceModel;
import softuni.workshopjudge2.model.service.ExerciseServiceModel;
import softuni.workshopjudge2.model.service.HomeworkServiceModel;
import softuni.workshopjudge2.model.service.UserServiceModel;
import softuni.workshopjudge2.service.CommentService;
import softuni.workshopjudge2.service.ExerciseService;
import softuni.workshopjudge2.service.HomeworkService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/homework")
public class HomeworkController {

    private final ExerciseService exerciseService;
    private final HomeworkService homeworkService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeworkController(ExerciseService exerciseService, HomeworkService homeworkService, CommentService commentService, ModelMapper modelMapper) {
        this.exerciseService = exerciseService;
        this.homeworkService = homeworkService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/add")
    public String homeworkAdd(Model model) {

        if (!model.containsAttribute("homeworkAddBidingModel")) {
            model.addAttribute("homeworkAddBidingModel", new HomeworkAddBidingModel());
            model.addAttribute("isLate", false);
        }
        model.addAttribute("allExNames", this.exerciseService.findAllExNames());
        return "homework-add";
    }

    @PostMapping("/add")
    public String confirmHomeworkAdd(@Valid @ModelAttribute("homeworkAddBidingModel")
                                             HomeworkAddBidingModel homeworkAddBidingModel,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession httpSession) {

        ExerciseServiceModel exercise = this.exerciseService.findByName(homeworkAddBidingModel.getExercise());
        boolean isLate = exercise.getDueDate().isBefore(LocalDateTime.now());
        if (bindingResult.hasErrors() || isLate) {
            redirectAttributes.addFlashAttribute("homeworkAddBidingModel", homeworkAddBidingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.homeworkAddBidingModel", bindingResult);
            redirectAttributes.addFlashAttribute("isLate", isLate);
            return "redirect:add";
        }
        HomeworkServiceModel homeworkServiceModel = this.modelMapper.map(homeworkAddBidingModel, HomeworkServiceModel.class);
        UserServiceModel user = this.modelMapper.map(httpSession.getAttribute("user"), UserServiceModel.class);
        homeworkServiceModel.setExercise(exercise);
        homeworkServiceModel.setAuthor(user);

        this.homeworkService.addHomework(homeworkServiceModel);

        return "redirect:/";
    }

    @GetMapping("/check")
    public String checkHomework(Model model) {

        if (!model.containsAttribute("commentAddBindingModel")) {
            model.addAttribute("commentAddBindingModel", new CommentAddBindingModel());
        }
        model.addAttribute("homework", this.homeworkService.findOneToCheck());

        return "homework-check";
    }

    @PostMapping("/check")
    public String confirmCheckHomework(@Valid @ModelAttribute("commentAddBindingModel") CommentAddBindingModel commentAddBindingModel,
                                       BindingResult bindingResult,
                                       RedirectAttributes redirectAttributes, HttpSession httpSession) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("commentAddBindingModel", commentAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.commentAddBindingModel", bindingResult);
            return "redirect:check";
        }

        CommentServiceModel commentServiceModel = this.modelMapper.map(commentAddBindingModel, CommentServiceModel.class);
        commentServiceModel.setHomework(this.homeworkService.findById(commentAddBindingModel.getHomeworkId()));
        commentServiceModel.setAuthor(this.modelMapper.map(httpSession.getAttribute("user"), UserServiceModel.class));

        this.commentService.addComment(commentServiceModel);
        return "redirect:/";
    }
}