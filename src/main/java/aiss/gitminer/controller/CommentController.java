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

@RestController
@RequestMapping("/gitminer/comments")
public class CommentController {
    @Autowired
    CommentRepository repository;

    public CommentController(CommentRepository repository){
        this.repository = repository;
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/COMMENTS
    @GetMapping
    public List<Comment> findAll(){
        return repository.findAll();
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/COMMENTS/{ID}
    @GetMapping("/{id}")
    public Comment findOne(@PathVariable String id){
        Optional<Comment> comment = repository.findById(id);
        return comment.get();
    }
}
