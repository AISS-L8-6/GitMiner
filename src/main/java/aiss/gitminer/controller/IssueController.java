package aiss.gitminer.controller;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Issue;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.IssueRepository;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "GitMiner Issues", description = "GitMiner issues management API")
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

    //GET HTTP://LOCALHOST:8080/GITMINER/ISSUES
    @Operation(
            summary = "Retrieve all issues",
            description = "Get all issues ",
            tags = {"issues","get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = IssueController.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/issues")
    public List<Issue> findAll(@RequestParam (required = false) String state,
                               @RequestParam(required = false) String authorId){
        if (state != null && authorId != null){
            List<Issue> issues = repository.findIssuesByAuthorId(authorId);
            return issues.stream().filter(x -> x.getState().equals(state)).toList();
        }
        //
        else if (authorId != null)
                return repository.findIssuesByAuthorId(authorId);
            else if (state != null)
                return repository.findIssuesByState(state);
            else
                return repository.findAll();
    }

    //GET HTTP://LOCALHOST:8080/GITMINER/ISSUES/{ID}
    @Operation(
            summary = "Retrieve an issues by id",
            description = "Get  an issues object by specifying its id ",
            tags = {"issues","get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = IssueController.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/issues/{id}")
    public Issue findOne(@Parameter(description = "id of the issue to be searched")@PathVariable String id) throws CommentNotFoundException {
        Optional<Issue> issue = repository.findById(id);
        if(!repository.existsById(id)){
            throw new CommentNotFoundException();
        }
        return issue.get();
    }

    //GET HTTP://LOCALHOST:8080/GITMINER/ISSUES/{id}/COMMENTS
    @Operation(
            summary = "Retrieve all issue comments",
            description = "Get all comments from an issue by specifying the issue Id",
            tags = {"issue","get"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = { @Content(schema = @Schema(implementation = CommentController.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @GetMapping("/issues/{id}/comments")
    public List<Comment> findCommentsFromIssueId(@Parameter(description = "id of the comments to be searched")@PathVariable String id) throws CommentNotFoundException {
        List<Comment> comments = repository.findById(id).get().getComments();
        if(!repository.existsById(id)){
            throw new CommentNotFoundException();
        }
        return comments;
    }

    //POST HTTP://LOCALHOST:8080/GITMINER/PROJECTS/{id}
    @Operation(
            summary = "Insert a issue in  project ",
            description = "Add a new issue whose data is passed in the body of the request in JSON format",
            tags = {"issue","post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success", content = { @Content(schema = @Schema(implementation = IssueController.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/projects/{id}/issues")
    public Issue create(@Valid @RequestBody Issue issue,@Parameter(description = "id of the issue to be sent") @PathVariable String id){
        Issue _issue = repository.save(new Issue(issue.getId(),issue.getRefId(),issue.getTitle(),issue.getDescription(),
                issue.getState(),issue.getCreatedAt(),issue.getUpdatedAt(),issue.getClosedAt(),issue.getLabels(),
                issue.getAuthor(),issue.getAssignee(),issue.getUpvotes(),issue.getDownvotes(),issue.getComments()));
        projectRepository.findById(id).get().getIssues().add(_issue);
        repository.save(_issue);
        return _issue;
    }

    //PUT HTTP://LOCALHOST:8080/GITMINER/ISSUES/{ID}
    @Operation(
            summary = "Update an issue",
            description = "Update an issue object by specifying its id and whose data is passed in the body of the request in JSON ",
            tags = {"issue","put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update",  content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/issues/{id}")
    public void update(@Valid @RequestBody Issue issue,
                       @Parameter(description = "id of the issue to be update ")  @PathVariable String id) throws CommentNotFoundException {
        Optional<Issue> commentIssue = repository.findById(id);
        if(!repository.existsById(id)){
            throw new CommentNotFoundException();
        }
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

    //PUT HTTP://LOCALHOST:8080/GITMINER/ISSUES/{ID}
    @Operation(
            summary = "Delete an issue",
            description = "Delete an issue object by specifying its id ",
            tags = {"issue","delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update",  content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/issues/{id}")
    @Transactional
    public  void  delete(@Parameter(description = "id of the issue to be delete")@PathVariable String id) throws CommentNotFoundException {
        if(!repository.existsById(id)){
            throw new CommentNotFoundException();
        }/*
        Issue issue = repository.findById(id).get();
        repository.deleteUser(issue.getAuthor().getId());
        if(!issue.getAssignee().equals(null)){
            repository.deleteUser(issue.getAssignee().getId());
        }
        for(int i = 0; i < issue.getComments().size(); i++){
            repository.deleteComment(issue.getComments().get(i).getId());
        }*/
        repository.deleteById(id);
    }
}
