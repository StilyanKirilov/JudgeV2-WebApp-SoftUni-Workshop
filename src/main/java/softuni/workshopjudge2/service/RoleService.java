package softuni.workshopjudge2.service;

import softuni.workshopjudge2.model.service.RoleServiceModel;

public interface RoleService {
    RoleServiceModel findByName(String name);
}
