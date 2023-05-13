package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer")
public class CommentController {
    @Autowired
    CommentRepository repository;
    @Autowired
    IssueRepository issueRepository;


    public CommentController(CommentRepository repository){
        this.repository = repository;
    }

    //GET HTTP://LOCALHOST:8080/GITMINER/COMMENTS
    @GetMapping("/comments")
    public List<Comment> findAll(){
        return repository.findAll();
    }

    //GET HTTP://LOCALHOST:8080/GITMINER/COMMENTS/{ID}
    @GetMapping("/comments/{id}")
    public Comment findOne(@PathVariable String id){
        Optional<Comment> comment = repository.findById(id);
        return comment.get();
    }

    //POST HTTP://LOCALHOST:8080/GITMINER/ISSUES/{id}
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/issues/{id}/comments")
    public Comment create(@Valid @RequestBody Comment comment, @PathVariable String issueId){
        Comment _comment = repository
                .save(new Comment(comment.getId(), comment.getBody(),comment.getAuthor(),comment.getCreatedAt(),comment.getUpdatedAt()));
        issueRepository.findById(issueId).get().getComments().add(_comment);
        repository.save(_comment);
        return _comment;
    }

    //PUT HTTP://LOCALHOST:8080/GITMINER/COMMENTS/{ID}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/comments/{id}")
    public void update(@Valid @RequestBody Comment updatedComment, @PathVariable String id) {
        Optional<Comment> commentData = repository.findById(id);
        Comment _comment = commentData.get();
        _comment.setBody(updatedComment.getBody());
        _comment.setAuthor(updatedComment.getAuthor());
        _comment.setCreatedAt(updatedComment.getCreatedAt());
        _comment.setUpdatedAt(updatedComment.getUpdatedAt());
        repository.save(_comment);
    }

    //DELETE HTTP://LOCALHOST:8080/GITMINER/COMMENTS/{ID}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/comments/{id}")
    public  void  delete(@PathVariable String id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
