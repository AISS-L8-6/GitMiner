package aiss.gitminer.controller;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.model.Commit;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommitRepository;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.repository.ProjectRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Retrieve all commit"  ,description = "Get all commit" ,tags = {"commit","get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = CommitController.class ),mediaType = "application/json")})
    })
    @GetMapping("/commits")
    public List<Commit> findAll(@RequestParam(required = false) String email){//
        if (email != null){
            return repository.findByEmail(email);
        }else {
            return repository.findAll();
        }
    }

    //GET HTTP://LOCALHOST:8080/GITMINER/COMMITS/{ID}
    @Operation(summary = "Retrieve commit by id"  ,description = "Get commit by id" ,tags = {"commit","get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = CommitController.class ),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",description = "Not Found", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping("/commits/{id}")
    public Commit findOne(@PathVariable String id) throws CommentNotFoundException {
        Optional<Commit> result = repository.findById(id);
        if(!result.isPresent()){
            throw new CommentNotFoundException();
        }
        return result.get();
    }

    //POST HTTP://LOCALHOST:8080/GITMINER/PROJECTS/{id}
    @Operation(
            summary = "Insert a commit in  project ",
            description = "Add a new issue whose data is passed in the body of the request in JSON format",
            tags = {"commit","post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success", content = { @Content(schema = @Schema(implementation = CommitController.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())})
    })
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
    @Operation(
            summary = "Update an Commit",
            description = "Update an Commit object by specifying its id and whose data is passed in the body of the request in JSON ",
            tags = {"Commit","put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update",  content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/commits/{id}")
    public void update(@Valid @RequestBody Commit updatedCommit, @PathVariable String id) throws CommentNotFoundException {
        Optional<Commit> commitData = repository.findById(id);
        if(!commitData.isPresent()){
            throw new CommentNotFoundException();
        }
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
    @Operation(
            summary = "Delete an Commit",
            description = "Delete an Commit object by specifying its id ",
            tags = {"Commit","delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update",  content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/commits/{id}")
    public void delete(@PathVariable String id) throws CommentNotFoundException {
        if(!repository.existsById(id)){
            throw new CommentNotFoundException();
        }
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
