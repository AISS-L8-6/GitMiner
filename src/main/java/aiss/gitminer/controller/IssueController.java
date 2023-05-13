package aiss.gitminer.controller;

import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer")
public class IssueController {

    @Autowired
    IssueRepository repository;

    @Autowired
    ProjectRepository projectRepository;

    public IssueController(IssueRepository repository){
        this.repository = repository;
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/ISSUES
    @GetMapping("/issues")
    public List<Issue> findAll(){
        return repository.findAll();
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/ISSUES/{ID}
    @GetMapping("/issues/{id}")
    public Issue findOne(@PathVariable String id){
        Optional<Issue> issue = repository.findById(id);
        return issue.get();
    }
    //GET HTTP/LOCALHOST:8080/GITMINER/ISSUES?STATE={STATE}
    @GetMapping(params = "state")
    public List<Issue> findIssueByState(@RequestParam ("state") String state){
        List<Issue> issues = repository.findIssuesByState(state);
        return issues;
    }
    //GET HTTP/LOCALHOST:8080/GITMINER/ISSUES/AUTHORID={AUTHORID}
    @GetMapping(params = "authorId")
    public List<Issue> findByAuthorId(@RequestParam("authorId") String authorId){
        List<Issue> issues = repository.findIssuesByAuthorId(authorId);
        return issues;
    }


    //GET HTTP/LOCALHOST:8080/GITMINER/ISSUES/{id}/COMMENTS
    @GetMapping("/issues/{id}/comments")
    public List<Comment> findCommentsFromIssueId(@PathVariable String id){
        List<Comment> comments = repository.findById(id).get().getComments();
        return comments;
    }

    //POST HTTP/LOCALHOST:8080/GITMINER/PROJECTS/{id}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/projects/{id}/issues")
    public Issue create(@Valid @RequestBody Issue issue, @PathVariable String projectId){
        Issue _issue = repository.save(new Issue(issue.getId(),issue.getRefId(),issue.getTitle(),issue.getDescription(),
                issue.getState(),issue.getCreatedAt(),issue.getUpdatedAt(),issue.getClosedAt(),issue.getLabels(),
                issue.getAuthor(),issue.getAssignee(),issue.getUpvotes(),issue.getDownvotes(),issue.getComments()));
        projectRepository.findById(projectId).get().getIssues().add(_issue);
        repository.save(_issue);
        return _issue;
    }
    //PUT HTTP/LOCALHOST:8080/GITMINER/ISSUES/{ID}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/issues/{id}")
    public void update(@Valid @RequestBody Issue issue, @PathVariable String id){
        Optional<Issue> commentIssue = repository.findById(id);
        Issue _issue = commentIssue.get();
        _issue.setRefId(issue.getRefId());
        _issue.setTitle(issue.getTitle());
        _issue.setDescription(issue.getDescription());
        _issue.setState(issue.getState());
        _issue.setCreatedAt(issue.getCreatedAt());
        _issue.setUpdatedAt(issue.getUpdatedAt());
        _issue.setClosedAt(issue.getClosedAt());
        _issue.setLabels(issue.getLabels());
        _issue.setAuthor(issue.getAuthor());
        _issue.setAssignee(issue.getAssignee());
        _issue.setUpvotes(issue.getUpvotes());
        _issue.setDownvotes(issue.getDownvotes());
        _issue.setComments(issue.getComments());
        repository.save(_issue);
    }

    //PUT HTTP/LOCALHOST:8080/GITMINER/ISSUES/{ID}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/issues/{id}")
    public  void  delete(@PathVariable String id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }



}
