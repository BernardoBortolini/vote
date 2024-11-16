package com.challenge.domain.services;

import com.challenge.domain.constants.TopicStatusEnum;
import com.challenge.domain.constants.VoteTypeEnum;
import com.challenge.domain.entities.Topic;
import com.challenge.domain.entities.Vote;
import com.challenge.infrastructure.repositories.TopicRepository;
import com.challenge.infrastructure.repositories.VoteRepository;
import com.challenge.presentation.dtos.VoteRequest;
import com.challenge.presentation.dtos.VoteResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

@Slf4j
@Service
public class VotingService {

    private final TopicRepository topicRepository;
    private final VoteRepository voteRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public VotingService(TopicRepository topicRepository, VoteRepository voteRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.topicRepository = topicRepository;
        this.voteRepository = voteRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Topic createTopic(Topic topic) {
        log.info("Creating topic with description: {}", topic.getDescription());
        topic.setStatus(TopicStatusEnum.CREATED);
        Topic savedTopic = topicRepository.save(topic);
        log.info("Topic created with ID: {}", savedTopic.getId());
        return savedTopic;
    }

    public void openSession(Long topicId, int durationInMinutes) {
        log.info("Opening session for topic ID: {} with duration: {} minutes", topicId, durationInMinutes);
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> {
                    log.error("Topic not found for ID: {}", topicId);
                    return new IllegalArgumentException("Topic not found");
                });
        topic.setStatus(TopicStatusEnum.OPEN);
        topic.setSessionStart(LocalDateTime.now());
        topic.setSessionEnd(LocalDateTime.now().plusMinutes(durationInMinutes));
        topicRepository.save(topic);
        log.info("Session opened for topic ID: {} with end time: {}", topicId, topic.getSessionEnd());
    }

    public void closeSession(Long topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new IllegalArgumentException("Topic not found"));
        topic.setStatus(TopicStatusEnum.CLOSED);
        topicRepository.save(topic);

        List<Vote> votes = voteRepository.findByTopicId(topicId);
        long yesVotes = votes.stream().filter(vote -> vote.getVote() == VoteTypeEnum.YES).count();
        long noVotes = votes.size() - yesVotes;

        VoteResult result = new VoteResult();
        result.setTopicId(topicId);
        result.setYesVotes((int) yesVotes);
        result.setNoVotes((int) noVotes);

        kafkaTemplate.send("voting-result", result);
    }

    public List<Vote> vote(List<VoteRequest> voteRequests) {
        log.info("Registering votes for {} requests", voteRequests.size());
        List<Vote> votesToSave = voteRequests.stream()
                .filter(voteRequest -> {
                    boolean exists = voteRepository.existsByTopicIdAndAssociateId(
                            voteRequest.getTopicId(),
                            voteRequest.getAssociateId()
                    );
                    if (exists) {
                        log.warn("Associate with ID: {} has already voted on topic ID: {}",
                                voteRequest.getAssociateId(), voteRequest.getTopicId());
                    }
                    return !exists;
                })
                .map(voteRequest -> {
                    Vote vote = new Vote();
                    vote.setTopicId(voteRequest.getTopicId());
                    vote.setAssociateId(voteRequest.getAssociateId());
                    vote.setVote(VoteTypeEnum.valueOf(voteRequest.getVote()));
                    return vote;
                })
                .toList();

        List<Vote> savedVotes = voteRepository.saveAll(votesToSave);
        log.info("Successfully registered {} votes", savedVotes.size());
        return savedVotes;
    }
}
