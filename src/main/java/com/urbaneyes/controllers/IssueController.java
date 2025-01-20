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

    // GET /api/issues - Listar todas as issues
    @GetMapping
    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    // GET /api/issues/{id} - Buscar uma issue específica pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Issue> getIssueById(@PathVariable Long id) {
        Optional<Issue> issue = issueRepository.findById(id);
        return issue.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/issues - Criar uma nova issue
    @PostMapping
    public Issue createIssue(@RequestBody Issue issue) {
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        issue.setStatus(IssueStatus.PENDING); // Define o status inicial como PENDING
        return issueRepository.save(issue);
    }
}
