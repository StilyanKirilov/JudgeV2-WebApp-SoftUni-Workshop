package softuni.workshopjudge2.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.workshopjudge2.model.entity.Homework;
import softuni.workshopjudge2.model.service.HomeworkServiceModel;
import softuni.workshopjudge2.repository.HomeworkRepository;
import softuni.workshopjudge2.service.HomeworkService;

import java.time.LocalDateTime;
import java.util.Comparator;

@Service
@Transactional
public class HomeworkServiceImpl implements HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeworkServiceImpl(HomeworkRepository homeworkRepository, ModelMapper modelMapper) {
        this.homeworkRepository = homeworkRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addHomework(HomeworkServiceModel homeworkServiceModel) {
        homeworkServiceModel.setAddedOn(LocalDateTime.now());
        this.homeworkRepository.saveAndFlush(this.modelMapper.map(homeworkServiceModel, Homework.class));
    }

    @Override
    public HomeworkServiceModel findOneToCheck() {
        return this.homeworkRepository
                .findAll()
                .stream()
                .min(Comparator.comparingInt(a -> a.getComments().size()))
                .map(homework -> this.modelMapper.map(homework, HomeworkServiceModel.class))
                .orElse(null);
    }

    @Override
    public HomeworkServiceModel findById(String homeworkId) {
        return this.homeworkRepository.findById(homeworkId)
                .map(homework -> this.modelMapper.map(homework, HomeworkServiceModel.class))
                .orElse(null);
    }
}