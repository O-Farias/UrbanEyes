package com.urbaneyes.service;

import com.urbaneyes.model.Issue;
import com.urbaneyes.model.IssueStatus;
import com.urbaneyes.repository.IssueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public Optional<Issue> getIssueById(Long id) {
        return issueRepository.findById(id);
    }

    public Issue createIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    public Issue updateIssue(Long id, Issue updatedIssue) {
        Issue existingIssue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        existingIssue.setTitle(updatedIssue.getTitle());
        existingIssue.setDescription(updatedIssue.getDescription());
        existingIssue.setCategory(updatedIssue.getCategory());
        existingIssue.setStatus(updatedIssue.getStatus());
        return issueRepository.save(existingIssue);
    }

    public void deleteIssue(Long id) {
        if (!issueRepository.existsById(id)) {
            throw new RuntimeException("Issue not found");
        }
        issueRepository.deleteById(id);
    }

    public List<Issue> getIssuesByCategory(Long categoryId) {
        return issueRepository.findByCategoryId(categoryId);
    }

    public Issue updateIssueStatus(Long id, IssueStatus status) {
        Issue existingIssue = issueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue not found"));
        existingIssue.setStatus(status);
        return issueRepository.save(existingIssue);
    }
}
