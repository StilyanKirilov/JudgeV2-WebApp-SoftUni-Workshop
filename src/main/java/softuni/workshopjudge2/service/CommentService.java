package softuni.workshopjudge2.service;

import softuni.workshopjudge2.model.service.CommentServiceModel;

import java.util.HashMap;

public interface CommentService {
    void addComment(CommentServiceModel commentServiceModel);

    Double getAvgScore();

    HashMap<Integer,Integer> getScoreMap();
}
