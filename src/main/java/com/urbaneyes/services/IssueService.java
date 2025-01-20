package com.urbaneyes.service;

import com.urbaneyes.model.Issue;
import com.urbaneyes.model.IssueStatus;
import com.urbaneyes.repository.IssueRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class IssueService {

    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
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
