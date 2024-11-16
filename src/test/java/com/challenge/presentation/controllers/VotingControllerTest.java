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
    void criarTopic_deveRetornarStatusOk() throws Exception {
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
    void abrirSessao_deveRetornarStatusOk() throws Exception {
        mockMvc.perform(post("/api/v1/voting/Topic/1/open")
                        .param("duracaoEmMinutos", "10"))
                .andExpect(status().isOk());

        verify(votingService, times(1)).openSession(1L, 10);
    }

    @Test
    void votar_deveRetornarStatusOk() throws Exception {
        VoteRequest votoRequest = new VoteRequest(1L, 1L, "YES");

        mockMvc.perform(post("/api/v1/voting/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"TopicId\": 1, \"associadoId\": 1, \"voto\": \"SIM\"}"))
                .andExpect(status().isOk());

        verify(votingService, times(1)).vote(any(VoteRequest.class));
    }

    @Test
    void encerrarSessao_deveRetornarStatusOk() throws Exception {
        mockMvc.perform(post("/api/v1/voting/topic/1/close"))
                .andExpect(status().isOk());

        verify(votingService, times(1)).closeSession(1L);
    }
}
