package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gitminer/comments")
public class CommentController {
    @Autowired
    CommentRepository repository;

    public CommentController(CommentRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Comment> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Comment findOne(@PathVariable String id){
        Optional<Comment> comment = repository.findById(id);
        return comment.get();
    }
}
