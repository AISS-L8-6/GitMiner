package aiss.gitminer.controller;

import aiss.gitminer.model.User;
import aiss.gitminer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gitminer/users")
public class UserController {
    @Autowired
    UserRepository repository;

    public UserController(UserRepository repository){
        this.repository = repository;
    }

    //GET HTTP/LOCALHOST:8080/GITMINER/USERS
    @GetMapping
    public List<User> findAll(){
        return repository.findAll();
    }


}
