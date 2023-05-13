package aiss.gitminer.controller;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/gitminer/users")
public class UserController {
    @Autowired
    UserRepository repository;

    public UserController(UserRepository repository){
        this.repository = repository;
    }

    //GET HTTP://LOCALHOST:8080/GITMINER/USERS
    @Operation(summary = "Retrieve all User"  ,description = "Get all User" ,tags = {"User","get"} )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success", content = {
                    @Content(schema = @Schema(implementation = UserController.class ),mediaType = "application/json")})
    })
    @GetMapping
    public List<User> findAll(){
        return repository.findAll();
    }

    //POST HTTP://LOCALHOST:8080/GITMINER/USERS
    @Operation(
            summary = "Insert a user in  issue ",
            description = "Add a new user whose data is passed in the body of the request in JSON format",
            tags = {"user","post"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Success", content = { @Content(schema = @Schema(implementation = UserController.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping
    public User create(@Valid @RequestBody User user){
        User newUser = repository.save(new User(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getAvatarUrl(),
                user.getWebUrl()
        ));
        return newUser;
    }

    //PUT HTTP://LOCALHOST:8080/GITMINER/USER/{ID}

    @Operation(
            summary = "Update an User",
            description = "Update an User object by specifying its id and whose data is passed in the body of the request in JSON ",
            tags = {"User","put"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update",  content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@Valid @RequestBody User updatedUser, @PathVariable String id) throws CommentNotFoundException {
        Optional<User> userData = repository.findById(id);
        if(!repository.existsById(id)){
            throw new CommentNotFoundException();
        }
        User _user = userData.get();
        _user.setUsername(updatedUser.getUsername());
        _user.setName(updatedUser.getName());
        _user.setAvatarUrl(updatedUser.getAvatarUrl());
        _user.setWebUrl(updatedUser.getWebUrl());
        repository.save(_user);
    }

    //DELETE HTTP://LOCALHOST:8080/GITMINER/USER/{ID}
    @Operation(
            summary = "Delete an User",
            description = "Delete an User object by specifying its id ",
            tags = {"User","delete"}
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update",  content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = { @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404", description = "Not Found", content = { @Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) throws CommentNotFoundException {
        if(!repository.existsById(id)){
            throw new CommentNotFoundException();
        }
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
    }

}
