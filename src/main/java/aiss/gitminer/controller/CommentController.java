package aiss.gitminer.controller;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.model.Comment;
import aiss.gitminer.model.Project;
import aiss.gitminer.repository.CommentRepository;
import aiss.gitminer.repository.IssueRepository;
import aiss.gitminer.repository.UserRepository;
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
public class CommentController {
    @Autowired
    CommentRepository repository;
    @Autowired
    IssueRepository issueRepository;


    public CommentController(CommentRepository repository){
        this.repository = repository;
    }

    //GET HTTP://LOCALHOST:8080/GITMINER/COMMENTS
    @Operation(summary = "Retrieve all comments"   ,tags = {"comment","get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = CommentController.class ),mediaType = "application/json")})
    })
    @GetMapping
    public List<Comment> findAll(){
        return repository.findAll();
    }

    //GET HTTP://LOCALHOST:8080/GITMINER/COMMENTS/{ID}
    @Operation(summary = "Retrieve comment by id"
            ,tags = {"commet","get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = CommentController.class ),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",description = "Not Found", content = {
                    @Content(schema = @Schema())
            })
    })
    @GetMapping("/{id}")
    public Comment findOne(@PathVariable String id) throws CommentNotFoundException {
        Optional<Comment> comment = repository.findById(id);
        if(!comment.isPresent()){
            throw new CommentNotFoundException();
        }
        return comment.get();
    }

    //POST HTTP://LOCALHOST:8080/GITMINER/ISSUES/{id}
    @Operation(
            summary = "Insert a comment in  issue ",
            description = "Add a new issue whose data is passed in the body of the request in JSON format",
            tags = {"comment","post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success", content = { @Content(schema = @Schema(implementation = CommentController.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/issues/{id}/comments")
    public Comment create(@Valid @RequestBody Comment comment, @PathVariable String id){
        Comment _comment = repository
                .save(new Comment(comment.getId(), comment.getBody(),comment.getAuthor(),comment.getCreatedAt(),comment.getUpdatedAt()));
        issueRepository.findById(id).get().getComments().add(_comment);
        repository.save(_comment);
        return _comment;
    }

    //PUT HTTP://LOCALHOST:8080/GITMINER/COMMENTS/{ID}
    @Operation(
            summary = "Update an Comment",
            description = "Update an Comment object by specifying its id and whose data is passed in the body of the request in JSON ",
            tags = {"Comment","put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update",  content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody Comment updatedComment, @PathVariable String id) throws CommentNotFoundException {
        Optional<Comment> commentData = repository.findById(id);
        if(!commentData.isPresent()){
            throw new CommentNotFoundException();
        }
        Comment _comment = commentData.get();
        _comment.setBody(updatedComment.getBody());
        _comment.setAuthor(updatedComment.getAuthor());
        _comment.setCreatedAt(updatedComment.getCreatedAt());
        _comment.setUpdatedAt(updatedComment.getUpdatedAt());
        repository.save(_comment);
    }

    //DELETE HTTP://LOCALHOST:8080/GITMINER/COMMENTS/{ID}
    @Operation(
            summary = "Delete an Comment",
            description = "Delete an Comment object by specifying its id ",
            tags = {"Comment","delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update",  content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public  void  delete(@PathVariable String id) throws CommentNotFoundException {
        if(!(repository.existsById(id))){
            throw new CommentNotFoundException();
        }
        if (repository.existsById(id)){
            repository.deleteById(id);
        }
    }
}
