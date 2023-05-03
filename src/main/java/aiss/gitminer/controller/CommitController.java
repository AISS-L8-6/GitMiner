package aiss.gitminer.controller;

import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

public class CommitController {

    private final CommitRepository repository;

    public CommitController(CommitRepository repository){
        this.repository = repository;
    }

    public List<Commit> findAll(){
        return repository.findAll();
    }

    public Commit findOne(String id){
        return repository.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Commit create(@Valid @RequestBody Commit commit) {
        return repository.create(commit);
    }
}
