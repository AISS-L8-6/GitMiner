package aiss.gitminer.controller;

import aiss.gitminer.model.Commit;
import aiss.gitminer.repository.CommitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/gitminer/commits")
public class CommitController {

    @Autowired
    CommitRepository repository;

    //GET HTTP/LOCALHOST:8080/GITMINER/COMMITS
    @GetMapping
    public List<Commit> findAll(){
        return repository.findAll();
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/COMMITS/{ID}
    @GetMapping("/{id}")
    public Commit findOne(@PathVariable String id){
        Optional<Commit> result = repository.findById(id);
        return result.get();
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/COMMITS?{EMAIL}
    @GetMapping("")
    public List<Commit> findByEmail(@RequestParam("email") String email){
        List<Commit> result = repository.findByEmail(email);
        return result;
    }
/*
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Commit create(@Valid @RequestBody Commit commit) {
        Commit result = repository
                .save(new Commit(commit.getId(), commit.getTitle(), commit.getMessage(), commit.getAuthorName(), commit.getAuthorEmail(), commit.getAuthoredDate(), commit.getCommitterName(), commit.getCommitterEmail(), commit.getCommittedDate(), commit.getWebUrl()));
        return result;
    }*/
}
