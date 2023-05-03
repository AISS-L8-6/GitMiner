package aiss.gitminer.controller;

import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository repository;

    public ProjectController(ProjectRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Project> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Project findOne(@PathVariable String id){
        return repository.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project) {
        return repository.create(project);
    }
}
