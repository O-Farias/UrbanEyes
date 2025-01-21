package com.urbaneyes.controller;

import com.urbaneyes.model.Issue;
import com.urbaneyes.service.IssueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        return issueService.getIssueById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Issue createIssue(@RequestBody Issue issue) {
        return issueService.createIssue(issue);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable Long id, @RequestBody Issue updatedIssue) {
        return ResponseEntity.ok(issueService.updateIssue(id, updatedIssue));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        issueService.deleteIssue(id);
        return ResponseEntity.noContent().build();
    }
}
