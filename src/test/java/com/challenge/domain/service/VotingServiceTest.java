package com.challenge.domain.service;

import com.challenge.domain.constants.TopicStatusEnum;
import com.challenge.domain.constants.VoteTypeEnum;
import com.challenge.domain.entities.Topic;
import com.challenge.domain.entities.Vote;
import com.challenge.domain.services.VotingService;
import com.challenge.infrastructure.repositories.TopicRepository;
import com.challenge.infrastructure.repositories.VoteRepository;
import com.challenge.presentation.dtos.VoteRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class VotingServiceTest {

    @InjectMocks
    private VotingService votingService;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTopic_mustSaveAndPersistTopic() {
        Topic topic = new Topic();
        topic.setDescription("New topic");

        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        Topic result = votingService.createTopic(topic);

        assertNotNull(result);
        assertEquals("Nova topic", result.getDescription());
        verify(topicRepository, times(1)).save(topic);
    }

    @Test
    void openSession_mustSetStatusAndAssignDuration() {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setStatus(TopicStatusEnum.CREATED);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(topicRepository.save(any(Topic.class))).thenReturn(topic);

        votingService.openSession(1L, 10);

        assertEquals(TopicStatusEnum.OPEN, topic.getStatus());
        assertNotNull(topic.getSessionStart());
        assertNotNull(topic.getSessionEnd());
        verify(topicRepository, times(1)).save(topic);
    }

    @Test
    void vote_mustRegisterNewVotesWhenNoDuplicatesExist() {
        List<VoteRequest> voteRequests = List.of(
                new VoteRequest(1L, 1L, "YES"),
                new VoteRequest(2L, 2L, "NO")
        );

        when(voteRepository.existsByTopicIdAndAssociateId(anyLong(), anyLong())).thenReturn(false);
        when(voteRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        List<Vote> votes = votingService.vote(voteRequests);

        assertNotNull(votes);
        assertEquals(2, votes.size());

        Vote firstVote = votes.get(0);
        assertEquals(1L, firstVote.getTopicId());
        assertEquals(1L, firstVote.getAssociateId());
        assertEquals(VoteTypeEnum.YES, firstVote.getVote());

        Vote secondVote = votes.get(1);
        assertEquals(2L, secondVote.getTopicId());
        assertEquals(2L, secondVote.getAssociateId());
        assertEquals(VoteTypeEnum.NO, secondVote.getVote());

        verify(voteRepository, times(1)).saveAll(anyList());
    }


    @Test
    void closeSession_SendResultToKafka() {
        Topic topic = new Topic();
        topic.setId(1L);
        topic.setStatus(TopicStatusEnum.OPEN);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(voteRepository.findByTopicId(1L)).thenReturn(List.of(
                new Vote(1L, 1L, 1L, VoteTypeEnum.YES),
                new Vote(1L, 2L, 2L, VoteTypeEnum.NO)
        ));

        votingService.closeSession(1L);

        assertEquals(TopicStatusEnum.CLOSED, topic.getStatus());
        verify(topicRepository, times(1)).save(topic);
        verify(kafkaTemplate, times(1)).send(eq("voting-result"), anyString());
    }
}
