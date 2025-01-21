package com.urbaneyes.service;

import com.urbaneyes.model.Category;
import com.urbaneyes.model.Issue;
import com.urbaneyes.model.IssueStatus;
import com.urbaneyes.repository.IssueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IssueServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueService issueService;

    private Issue issue;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setId(1L);
        category.setName("Infrastructure");

        issue = new Issue();
        issue.setId(1L);
        issue.setTitle("Broken Streetlight");
        issue.setDescription("A streetlight is broken.");
        issue.setCategory(category);
        issue.setStatus(IssueStatus.OPEN);
    }

    @Test
    void shouldGetAllIssues() {
        when(issueRepository.findAll()).thenReturn(List.of(issue));

        List<Issue> issues = issueService.getAllIssues();

        assertNotNull(issues);
        assertEquals(1, issues.size());
        assertEquals("Broken Streetlight", issues.get(0).getTitle());
        verify(issueRepository, times(1)).findAll();
    }

    @Test
    void shouldGetIssueById() {
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));

        Optional<Issue> fetchedIssue = issueService.getIssueById(1L);

        assertTrue(fetchedIssue.isPresent());
        assertEquals("Broken Streetlight", fetchedIssue.get().getTitle());
        verify(issueRepository, times(1)).findById(1L);
    }

    @Test
    void shouldReturnEmptyWhenIssueNotFound() {
        when(issueRepository.findById(2L)).thenReturn(Optional.empty());

        Optional<Issue> fetchedIssue = issueService.getIssueById(2L);

        assertFalse(fetchedIssue.isPresent());
        verify(issueRepository, times(1)).findById(2L);
    }

    @Test
    void shouldCreateIssue() {
        when(issueRepository.save(issue)).thenReturn(issue);

        Issue savedIssue = issueService.createIssue(issue);

        assertNotNull(savedIssue);
        assertEquals("Broken Streetlight", savedIssue.getTitle());
        verify(issueRepository, times(1)).save(issue);
    }

    @Test
    void shouldUpdateIssue() {
        Issue updatedIssue = new Issue();
        updatedIssue.setTitle("Fixed Streetlight");
        updatedIssue.setDescription("The streetlight has been fixed.");
        updatedIssue.setCategory(category);
        updatedIssue.setStatus(IssueStatus.CLOSED);

        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
        when(issueRepository.save(issue)).thenReturn(issue);

        Issue result = issueService.updateIssue(1L, updatedIssue);

        assertNotNull(result);
        assertEquals("Fixed Streetlight", result.getTitle());
        assertEquals(IssueStatus.CLOSED, result.getStatus());
        verify(issueRepository, times(1)).findById(1L);
        verify(issueRepository, times(1)).save(issue);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentIssue() {
        when(issueRepository.findById(2L)).thenReturn(Optional.empty());

        Issue updatedIssue = new Issue();
        updatedIssue.setTitle("Fixed Streetlight");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            issueService.updateIssue(2L, updatedIssue);
        });

        assertEquals("Issue not found", exception.getMessage());
        verify(issueRepository, times(1)).findById(2L);
    }

    @Test
    void shouldDeleteIssue() {
        when(issueRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> issueService.deleteIssue(1L));
        verify(issueRepository, times(1)).existsById(1L);
        verify(issueRepository, times(1)).deleteById(1L);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentIssue() {
        when(issueRepository.existsById(2L)).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            issueService.deleteIssue(2L);
        });

        assertEquals("Issue not found", exception.getMessage());
        verify(issueRepository, times(1)).existsById(2L);
    }

    @Test
    void shouldGetIssuesByCategory() {
        when(issueRepository.findByCategoryId(1L)).thenReturn(List.of(issue));

        List<Issue> issues = issueService.getIssuesByCategory(1L);

        assertNotNull(issues);
        assertEquals(1, issues.size());
        assertEquals("Broken Streetlight", issues.get(0).getTitle());
        verify(issueRepository, times(1)).findByCategoryId(1L);
    }

    @Test
    void shouldUpdateIssueStatus() {
        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
        when(issueRepository.save(issue)).thenReturn(issue);

        Issue result = issueService.updateIssueStatus(1L, IssueStatus.CLOSED);

        assertNotNull(result);
        assertEquals(IssueStatus.CLOSED, result.getStatus());
        verify(issueRepository, times(1)).findById(1L);
        verify(issueRepository, times(1)).save(issue);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingStatusForNonExistentIssue() {
        when(issueRepository.findById(2L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            issueService.updateIssueStatus(2L, IssueStatus.CLOSED);
        });

        assertEquals("Issue not found", exception.getMessage());
        verify(issueRepository, times(1)).findById(2L);
    }
}
