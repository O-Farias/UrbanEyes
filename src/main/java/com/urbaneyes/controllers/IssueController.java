package com.urbaneyes.controller;

import com.urbaneyes.model.Issue;
import com.urbaneyes.model.IssueStatus;
import com.urbaneyes.repository.IssueRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueRepository issueRepository;

    public IssueController(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    // GET /api/issues
    @GetMapping
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    // GET /api/issues/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        if (issue.isPresent()) {
            return ResponseEntity.ok(issue.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/issues
    @PostMapping
    public Issue createIssue(@RequestBody Issue issue) {
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        issue.setStatus(IssueStatus.PENDING);
        return issueRepository.save(issue);
    }

    // PUT /api/issues/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Issue> updateIssue(@PathVariable Long id, @RequestBody Issue updatedIssue) {
        Optional<Issue> existingIssue = issueRepository.findById(id);
        if (existingIssue.isPresent()) {
            Issue issue = existingIssue.get();
            issue.setTitle(updatedIssue.getTitle());
            issue.setDescription(updatedIssue.getDescription());
            issue.setStatus(updatedIssue.getStatus());
            issue.setCategory(updatedIssue.getCategory());
            issue.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(issueRepository.save(issue));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE /api/issues/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
        if (issueRepository.existsById(id)) {
            issueRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
