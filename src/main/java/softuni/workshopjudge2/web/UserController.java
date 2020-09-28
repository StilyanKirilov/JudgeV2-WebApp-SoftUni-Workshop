package softuni.workshopjudge2.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import softuni.workshopjudge2.model.binding.UserAddBindingModel;
import softuni.workshopjudge2.model.binding.UserLoginBindingModel;
import softuni.workshopjudge2.model.service.UserServiceModel;
import softuni.workshopjudge2.model.view.UserProfileViewModel;
import softuni.workshopjudge2.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute("userLoginBindingModel")
                                      UserLoginBindingModel userLoginBindingModel,
                              BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.addObject("userLoginBindingModel", userLoginBindingModel);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView loginConfirm(@Valid @ModelAttribute("userLoginBindingModel")
                                             UserLoginBindingModel userLoginBindingModel,
                                     BindingResult bindingResult, ModelAndView modelAndView,
                                     HttpSession httpSession, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
            modelAndView.setViewName("redirect:/users/login");
        } else {
            UserServiceModel user = this.userService
                    .findByUsername(userLoginBindingModel.getUsername());
            if (user == null || !user.getPassword().equals(userLoginBindingModel.getPassword())) {
                redirectAttributes.addFlashAttribute("notFound", true);
                redirectAttributes.addFlashAttribute("userLoginBindingModel", userLoginBindingModel);
                modelAndView.setViewName("redirect:/users/login");
            } else {
                httpSession.setAttribute("user", user);
                httpSession.setAttribute("id", user.getId());
                httpSession.setAttribute("role", user.getRole().getName());
                modelAndView.setViewName("redirect:/");
            }
        }
        return modelAndView;

    }

    @GetMapping("/register")
    public String register(@Valid @ModelAttribute("userAddBindingModel")
                                   UserAddBindingModel userAddBindingModel,
                           BindingResult bindingResult) {
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@Valid @ModelAttribute("userAddBindingModel")
                                                UserAddBindingModel userAddBindingModel,
                                        BindingResult bindingResult, ModelAndView modelAndView, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userAddBindingModel", userAddBindingModel);
            modelAndView.setViewName("redirect:/users/register");
        } else {
            this.userService.registerUser(this.modelMapper.map(userAddBindingModel, UserServiceModel.class));
            modelAndView.setViewName("redirect:/users/login");
        }
        return modelAndView;
    }

    @RequestMapping("/logout")
    public String logoutUser(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profileUser(Model model, @RequestParam("id") String id) {
        UserServiceModel byId = this.userService.findById(id);
        UserProfileViewModel view = this.modelMapper
                .map(byId, UserProfileViewModel.class);
        view.setHomeworks(byId.getHomeworks()
                .stream()
                .map(homework -> homework.getExercise().getName())
                .collect(Collectors.joining(", ")));

        model.addAttribute("user", view);

        return "profile";
    }
}
