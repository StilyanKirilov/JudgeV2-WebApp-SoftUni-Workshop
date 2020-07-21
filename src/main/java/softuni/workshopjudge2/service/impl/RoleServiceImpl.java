package softuni.workshopjudge2.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshopjudge2.model.entity.Role;
import softuni.workshopjudge2.model.service.RoleServiceModel;
import softuni.workshopjudge2.repository.RoleRepository;
import softuni.workshopjudge2.service.RoleService;

import javax.annotation.PostConstruct;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    public void init() {
        if (this.roleRepository.count() == 0) {
            Role adminRole = new Role("ADMIN");
            Role userRole = new Role("USER");

            this.roleRepository.save(adminRole);
            this.roleRepository.save(userRole);
        }
    }

    @Override
    public RoleServiceModel findByName(String name) {
        return this.modelMapper.map(roleRepository.findByName(name), RoleServiceModel.class);
    }
}
