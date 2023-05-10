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
@RequestMapping("/gitminer/issues")
public class IssueController {

    @Autowired
    IssueRepository repository;

    public IssueController(IssueRepository repository){
        this.repository = repository;
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/ISSUES
    @GetMapping
    public List<Issue> findAll(){
        return repository.findAll();
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/ISSUES/{ID}
    @GetMapping("/{id}")
    public Issue findOne(@PathVariable String id){
        Optional<Issue> issue = repository.findById(id);
        return issue.get();
    }
    @GetMapping(params = "state")
    public List<Issue> findIssueByState(@RequestParam (name = "state") String state){
        List<Issue> issues = repository.findAll();
        List<Issue> issuesByState = issues.stream().filter(issue -> issue.getState().equals(state)).toList();
        return issuesByState;
    }
    //GET HTTP/LOCALHOST:8080/GITMINER/ISSUES?={AUTHORID}
    /*@GetMapping
    public List<Issue> findByAuthorId(@RequestParam("authorId") String authorId){
        List<Issue> issues = repository.findIssuesByAuthorId(authorId);
        return issues;
    }*/

}
