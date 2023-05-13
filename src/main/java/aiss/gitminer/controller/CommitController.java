package aiss.gitminer.controller;

import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/")
public class CommitController {

    @Autowired
    CommitRepository repository;

    @Autowired
    ProjectRepository projectRepository;

    public CommitController(CommitRepository repository){
        this.repository = repository;
    }

    //GET HTTP://LOCALHOST:8080/GITMINER/COMMITS
    @GetMapping("/commits")
    public List<Commit> findAll(@RequestParam(required = false) String email){//
        if (email != null){
            return repository.findByEmail(email);
        }else {
            return repository.findAll();
        }
    }

    //GET HTTP://LOCALHOST:8080/GITMINER/COMMITS/{ID}
    @GetMapping("/commits/{id}")
    public Commit findOne(@PathVariable String id){
        Optional<Commit> result = repository.findById(id);
        return result.get();
    }

    //POST HTTP://LOCALHOST:8080/GITMINER/PROJECTS/
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Commit create(@Valid @RequestBody Commit commit) {
        Commit result = repository
                .save(new Commit(commit.getId(), commit.getTitle(), commit.getMessage(), commit.getAuthorName(), commit.getAuthorEmail(), commit.getAuthoredDate(), commit.getCommitterName(), commit.getCommitterEmail(), commit.getCommittedDate(), commit.getWebUrl()));
        projectRepository.
        return result;
    }
}
