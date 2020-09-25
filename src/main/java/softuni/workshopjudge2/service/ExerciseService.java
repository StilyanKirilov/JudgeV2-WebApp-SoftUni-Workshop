package softuni.workshopjudge2.service;

import softuni.workshopjudge2.model.service.ExerciseServiceModel;

import java.util.List;

public interface ExerciseService {
    void addExercise(ExerciseServiceModel exercise);

    List<String> findAllExNames();

    ExerciseServiceModel findByName(String name);
}
