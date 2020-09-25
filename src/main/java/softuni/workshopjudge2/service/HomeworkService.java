package softuni.workshopjudge2.service;

import softuni.workshopjudge2.model.service.HomeworkServiceModel;

public interface HomeworkService {
    void addHomework(HomeworkServiceModel homeworkServiceModel);

    HomeworkServiceModel findOneToCheck();

    HomeworkServiceModel findById(String homeworkId);
}
