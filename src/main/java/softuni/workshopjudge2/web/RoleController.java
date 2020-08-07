package softuni.workshopjudge2.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshopjudge2.model.binding.RoleAddBindingModel;
import softuni.workshopjudge2.service.RoleService;
import softuni.workshopjudge2.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleController(RoleService roleService, UserService userService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView) {
        modelAndView.addObject("usernames", this.userService.findAllUsernames());
        modelAndView.setViewName("role-add");
        return modelAndView;
    }

    @PostMapping("/add")
    public String addConfirm(@ModelAttribute("roleAddBindingModel") RoleAddBindingModel
                                     roleAddBindingModel) {

        if (this.userService.findByUsername(roleAddBindingModel.getUsername()) != null) {
            this.userService
                    .addRoleToUser(roleAddBindingModel.getUsername(), roleAddBindingModel.getRole());
            return "redirect:/";
        } else {
            throw new EntityNotFoundException(String.format("User with username: %s does not exist!", roleAddBindingModel.getUsername()));
        }
    }

}
