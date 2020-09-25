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

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public UserServiceModel findByUsername(String username) {
        return this.userRepository
                .findByUsername(username)
                .map(user -> this.modelMapper
                        .map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public List<String> findAllUsernames() {
        return this.userRepository
                .findAll()
                .stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    @Override
    public void addRoleToUser(String username, String role) {
        User user = this.userRepository.findByUsername(username).orElse(null);
        Role roleEntity = this.modelMapper.map(this.roleService.findByName(role), Role.class);

        if (!user.getRole().getName().equals(role)) {
            user.setRole(roleEntity);
            this.userRepository
                    .saveAndFlush(user);
        }
    }

    @Override
    public UserServiceModel findById(String id) {
        return this.userRepository.findById(id)
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }
}
