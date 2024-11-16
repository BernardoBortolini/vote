package com.challenge.presentation.controllers;


import com.challenge.domain.entities.Topic;
import com.challenge.domain.services.VoterStatusService;
import com.challenge.domain.services.VotingService;
import com.challenge.presentation.dtos.VoteRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/voting")
public class VotingController {

    private final VotingService votingService;
    private final VoterStatusService voterStatusService;

    public VotingController(VotingService votingService, VoterStatusService voterStatusService) {
        this.votingService = votingService;
        this.voterStatusService = voterStatusService;
    }

    @PostMapping("/topic")
    public ResponseEntity<Topic> createTopic(@RequestBody Topic topic) {
        return ResponseEntity.ok(votingService.createTopic(topic));
    }

    @PostMapping("/topic/{topicId}/open")
    public ResponseEntity<Void> openSession(@PathVariable Long topicId, @RequestParam int durationInMinutes) {
        votingService.openSession(topicId, durationInMinutes);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/vote")
    public ResponseEntity<Void> vote(@RequestBody VoteRequest voteRequest) {
        votingService.vote(voteRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/topic/{topicId}/close")
    public ResponseEntity<Void> closeSession(@PathVariable Long topicId) {
        votingService.closeSession(topicId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/voter-status/{cpf}")
    public String getVoterStatus(@PathVariable String cpf) {
        return voterStatusService.checkVoterStatus(cpf);
    }
}
