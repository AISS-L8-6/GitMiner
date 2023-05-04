package aiss.gitminer.controller;

import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository repository;

    public ProjectController(ProjectRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public List<Project> findAll(){
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Project findOne(@PathVariable long id){
        Optional<Project> project = repository.findById(id);
        return project.get();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project) {
        Project result = repository
                .save(new Project(project.getId(), project.getName(), project.getWebUrl(), project.getCommits(), project.getIssues()));
        return result;
    }
/*
    //PUT HTTP/LOCALHOST:8080/PROJECTS/ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("{/id}")
    public void update(@Valid @RequestBody Project updatedProject, @PathVariable long id) {
        Optional<Project> projectData = repository.findById(id);
        Project _project = projectData.get();
        _project.setName(updatedProject.getName());
        //_project.setDescription(updatedProject.getDescription());
        repository.save(_project);
    }

    //DELETE HTTP/LOCALHOST:8080/PROJECTS/ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{/id}")
    public void update(@PathVariable long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }
 */
}
