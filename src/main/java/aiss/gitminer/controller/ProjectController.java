package aiss.gitminer.controller;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name="GitMiner Projects", description = "GitMiner projects management API")
@RestController
@RequestMapping("/gitminer/projects")
public class ProjectController {

    @Autowired
    ProjectRepository repository;

    public ProjectController(ProjectRepository repository){
        this.repository = repository;
    }

    //POST HTTP/LOCALHOST:8080/GITMINER/PROJECTS
    @Operation(summary = "Inject a project"  ,
            description = "Load project to GitMiner whose data is passed as body request in JSON format " ,
            tags = {"project","post"} )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created", content = {
                    @Content(schema = @Schema(implementation = ProjectController.class ),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",description = "Bad Request", content = {
                    @Content(schema = @Schema())
            })
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Project create(@Valid @RequestBody Project project) {
        Project newProject = repository.save(new Project(
                project.getId(),
                project.getName(),
                project.getWebUrl(),
                project.getCommits(),
                project.getIssues()));
        return newProject;
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/PROJECTS
    @GetMapping
    public List<Project> findAll(){
        return repository.findAll();
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/PROJECTS/ID
    @Operation(summary = "Retrieve project by id"  ,description = "Get project by id" ,tags = {"project","get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = ProjectController.class ),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",description = "Not Found", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping("/{id}")
    public Project findOne(@Parameter(description = "id of the project to be searched")@PathVariable String id) throws CommentNotFoundException {
        Optional<Project> project = repository.findById(id);
        if(!repository.existsById(id)){
            throw new CommentNotFoundException();
        }
        return project.get();
    }


    //PUT HTTP://LOCALHOST:8080/PROJECTS/{ID}
    @Operation(
            summary = "Update an Project",
            description = "Update an Project object by specifying its id and whose data is passed in the body of the request in JSON ",
            tags = {"Project","put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update",  content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Project updatedProject, @Parameter(description = "id of the project to be update ") @PathVariable String id) throws CommentNotFoundException {
        Optional<Project> projectData = repository.findById(id);
        if(!repository.existsById(id)){
            throw new CommentNotFoundException();
        }
        Project _project = projectData.get();
        _project.setName(updatedProject.getName());
        _project.setWebUrl(updatedProject.getWebUrl());
        _project.setCommits(updatedProject.getCommits());
        _project.setIssues(updatedProject.getIssues());
        repository.save(_project);
    }

    //DELETE HTTP://LOCALHOST:8080/PROJECTS/{ID}
    @Operation(
            summary = "Delete an Project",
            description = "Delete an Project object by specifying its id ",
            tags = {"Project","delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update",  content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "id of the project to be delete")@PathVariable String id) throws CommentNotFoundException {
        if(!repository.existsById(id)){
            throw new CommentNotFoundException();
        }
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }

}
