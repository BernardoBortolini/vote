package com.challenge.presentation.controllers;

import com.challenge.domain.entities.Agenda;
import com.challenge.domain.entities.VotingSession;
import com.challenge.domain.services.VotingSessionService;
import com.challenge.infrastructure.repositories.AgendaRepository;
import com.challenge.infrastructure.repositories.VotingSessionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class VotingSessionController {

    @Autowired
    private VotingSessionService votingSessionService;

    @PostMapping("/{agendaId}")
    public ResponseEntity<VotingSession> openSession(@PathVariable Long agendaId, @RequestParam int minuts) {
        return ResponseEntity.ok(votingSessionService.openSession(agendaId, minuts));
    }
}
