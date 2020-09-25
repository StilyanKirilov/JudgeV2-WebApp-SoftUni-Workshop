package softuni.workshopjudge2.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshopjudge2.model.entity.Comment;
import softuni.workshopjudge2.model.service.CommentServiceModel;
import softuni.workshopjudge2.repository.CommentRepository;
import softuni.workshopjudge2.service.CommentService;

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
}
