package softuni.workshopjudge2.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshopjudge2.model.entity.Comment;
import softuni.workshopjudge2.model.service.CommentServiceModel;
import softuni.workshopjudge2.repository.CommentRepository;
import softuni.workshopjudge2.service.CommentService;

import java.util.HashMap;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addComment(CommentServiceModel commentServiceModel) {
        this.commentRepository.saveAndFlush(this.modelMapper.map(commentServiceModel, Comment.class));
    }

    @Override
    public Double getAvgScore() {

        return this.commentRepository
                .findAll()
                .stream()
                .mapToDouble(Comment::getScore)
                .average()
                .orElse(0D);
    }

    @Override
    public HashMap<Integer, Integer> getScoreMap() {
        HashMap<Integer, Integer> map = new HashMap<>();

        this.commentRepository
                .findAll()
                .forEach(comment -> {
                            int score = comment.getScore();
                            map.put(score,
                                    map.containsKey(score)
                                            ? map.get(score) + 1 : 1);
                        }
                );

        return map;
    }
}
