package com.urbaneyes.controller;

import com.urbaneyes.model.Issue;
import com.urbaneyes.repository.IssueRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueRepository issueRepository;

    public IssueController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    @GetMapping
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    @PostMapping
    public Issue createIssue(@RequestBody Issue issue) {
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        issue.setStatus(IssueStatus.PENDING); 
        return issueRepository.save(issue);
    }
}
