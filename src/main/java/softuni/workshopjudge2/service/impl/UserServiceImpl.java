package softuni.workshopjudge2.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshopjudge2.model.entity.Role;
import softuni.workshopjudge2.model.entity.User;
import softuni.workshopjudge2.model.service.RoleServiceModel;
import softuni.workshopjudge2.model.service.UserServiceModel;
import softuni.workshopjudge2.repository.UserRepository;
import softuni.workshopjudge2.service.RoleService;
import softuni.workshopjudge2.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, RoleService roleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        userServiceModel
                .setRole(this.modelMapper.map(this.roleService.findByName(this.userRepository.count() == 0 ? "ADMIN" : "USER"), RoleServiceModel.class));
        User user = this.modelMapper
                .map(userServiceModel, User.class);

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }
}
