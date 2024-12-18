package com.challenge.presentation.controllers;

import com.challenge.domain.entities.Topic;
import com.challenge.domain.services.VotingService;
import com.challenge.presentation.dtos.VoteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VotingControllerTest {

    @InjectMocks
    private VotingController votingController;

    @Mock
    private VotingService votingService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(votingController).build();
    }

    @Test
    void createTopic_mustReturnStatusOk() throws Exception {
        Topic Topic = new Topic();
        Topic.setDescription("New Topic");

        when(votingService.createTopic(any(Topic.class))).thenReturn(Topic);

        mockMvc.perform(post("/api/v1/voting/topic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\": \"Nova Topic\"}"))
                .andExpect(status().isOk());

        verify(votingService, times(1)).createTopic(any(Topic.class));
    }

    @Test
    void vote_mustReturnStatusOk() throws Exception {
        String content = "[{\"topicId\": 1, \"associateId\": 1, \"vote\": \"YES\"},"
                + " {\"topicId\": 2, \"associateId\": 2, \"vote\": \"NO\"}]";

        mockMvc.perform(post("/api/v1/voting/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());

        verify(votingService, times(1)).vote(anyList());
    }


    @Test
    void closeSessao_mustReturnStatusOk() throws Exception {
        mockMvc.perform(post("/api/v1/voting/topic/1/close"))
                .andExpect(status().isOk());

        verify(votingService, times(1)).closeSession(1L);
    }
}
