package com.urbaneyes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; 
import com.urbaneyes.model.Issue;
import com.urbaneyes.model.IssueStatus;
import com.urbaneyes.service.IssueService;
import org.junit.jupiter.api.BeforeEach; 
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IssueController.class)
class IssueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IssueService issueService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule()); 
    }

    @Test
    void shouldGetAllIssues() throws Exception {
        Issue issue1 = new Issue(1L, "Broken Streetlight", "A streetlight is broken.", IssueStatus.OPEN);
        Issue issue2 = new Issue(2L, "Pothole", "A large pothole on Main Street.", IssueStatus.OPEN);

        Mockito.when(issueService.getAllIssues()).thenReturn(List.of(issue1, issue2));

        mockMvc.perform(get("/api/issues"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].title").value("Broken Streetlight"))
                .andExpect(jsonPath("$[1].title").value("Pothole"));
    }

    @Test
    void shouldGetIssueById() throws Exception {
        Issue issue = new Issue(1L, "Broken Streetlight", "A streetlight is broken.", IssueStatus.OPEN);

        Mockito.when(issueService.getIssueById(eq(1L))).thenReturn(Optional.of(issue));

        mockMvc.perform(get("/api/issues/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Broken Streetlight"))
                .andExpect(jsonPath("$.description").value("A streetlight is broken."));
    }

    @Test
    void shouldReturnNotFoundForInvalidIssueId() throws Exception {
        Mockito.when(issueService.getIssueById(eq(999L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/issues/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateIssue() throws Exception {
        Issue issue = new Issue(null, "Pothole", "A large pothole on Main Street.", IssueStatus.OPEN);
        Issue savedIssue = new Issue(1L, "Pothole", "A large pothole on Main Street.", IssueStatus.OPEN);

        Mockito.when(issueService.createIssue(any(Issue.class))).thenReturn(savedIssue);

        mockMvc.perform(post("/api/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(issue)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Pothole"))
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @Test
    void shouldUpdateIssue() throws Exception {
        Issue updatedIssue = new Issue(1L, "Fixed Pothole", "The pothole has been fixed.", IssueStatus.CLOSED);

        Mockito.when(issueService.updateIssue(eq(1L), any(Issue.class))).thenReturn(updatedIssue);

        mockMvc.perform(put("/api/issues/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIssue)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Fixed Pothole"))
                .andExpect(jsonPath("$.status").value("CLOSED"));
    }

    @Test
    void shouldDeleteIssue() throws Exception {
        mockMvc.perform(delete("/api/issues/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(issueService).deleteIssue(1L);
    }
}
