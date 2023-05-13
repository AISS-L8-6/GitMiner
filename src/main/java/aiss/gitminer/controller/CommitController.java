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
@RequestMapping("/gitminer")
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

    //POST HTTP://LOCALHOST:8080/GITMINER/PROJECTS/{id}
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/projects/{id}/commit")
    public Commit create(@Valid @RequestBody Commit commit,@PathVariable String id) {
        Commit result = repository
                .save(new Commit(commit.getId(), commit.getTitle(), commit.getMessage(), commit.getAuthorName(), commit.getAuthorEmail(), commit.getAuthoredDate(), commit.getCommitterName(), commit.getCommitterEmail(), commit.getCommittedDate(), commit.getWebUrl()));
        projectRepository.findById(id).get().getCommits().add(result);
        repository.save(result);
        return result;
    }

    //PUT HTTP://LOCALHOST:8080/GITMINER/COMMITS/{ID}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/commits/{id}")
    public void update(@Valid @RequestBody Commit updatedCommit, @PathVariable String id){
        Optional<Commit> commitData = repository.findById(id);
        Commit _commit = commitData.get();
        _commit.setTitle(updatedCommit.getTitle());
        _commit.setMessage(updatedCommit.getMessage());
        _commit.setAuthorName(updatedCommit.getAuthorName());
        _commit.setAuthorEmail(updatedCommit.getAuthorEmail());
        _commit.setAuthoredDate(updatedCommit.getAuthoredDate());
        _commit.setCommitterName(updatedCommit.getCommitterName());
        _commit.setCommitterEmail(updatedCommit.getCommitterEmail());
        _commit.setCommittedDate(updatedCommit.getCommittedDate());
        _commit.setWebUrl(updatedCommit.getWebUrl());
        repository.save(_commit);
    }

    //DELETE HTTP://LOCALHOST:8080/GITMINER/COMMITS/{ID}
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/commits/{id}")
    public void delete(@PathVariable String id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
