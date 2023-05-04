package aiss.gitminer.controller;

import aiss.gitminer.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gitminer/issue")
public class IssueController {

    @Autowired
    IssueRepository repository;
}
