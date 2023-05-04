package aiss.gitminer.controller;

import aiss.gitminer.model.Issue;
import aiss.gitminer.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
