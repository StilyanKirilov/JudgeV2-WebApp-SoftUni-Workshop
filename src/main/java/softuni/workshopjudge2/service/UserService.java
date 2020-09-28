package softuni.workshopjudge2.service;

import softuni.workshopjudge2.model.service.UserServiceModel;

import java.util.List;

public interface UserService {

    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findByUsername(String username);

    List<String> findAllUsernames();

    void addRoleToUser(String username, String role);

    UserServiceModel findById(String id);

    Long findTotalUsersCount();
}
