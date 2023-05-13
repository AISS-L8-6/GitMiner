package aiss.gitminer.controller;

import aiss.gitminer.exception.CommentNotFoundException;
import aiss.gitminer.model.User;
import aiss.gitminer.repository.UserRepository;
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
    @GetMapping
    public List<User> findAll(){
        return repository.findAll();
    }

    //POST HTTP://LOCALHOST:8080/GITMINER/USERS
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
