package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/issue")
public class IssueController {

    @Autowired
    IssueRepository repository;

    public IssueController(IssueRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Issue> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Issue findOne(@PathVariable String id){
        Optional<Issue> issue = repository.findById(id);
        return issue.get();
    }
    @GetMapping("/{id}/comments")
    public List<Comment> findComment(@PathVariable String id){
        Optional<Issue> issue = repository.findById(id);
        return issue.get().getComments();
    }
    @GetMapping("issue?/{authorId}")
    public List<Issue> findIssueByAuthorId(@RequestParam(name = "authorId") String AuthorId ) {
        List<Issue> issues = repository.findAll();
        return issues.filter(x -> x.getAuthor = AuthorId);
    }

}
