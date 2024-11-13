package com.challenge.presentation.controllers;

import com.challenge.domain.entities.Agenda;
import com.challenge.domain.entities.Vote;
import com.challenge.domain.services.VoteService;
import com.challenge.infrastructure.repositories.AgendaRepository;
import com.challenge.infrastructure.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AgendaRepository agendaRepository;


    @Autowired
    private VoteService voteService;

    @PostMapping("/{agendaId}/{associetedId}")
    public ResponseEntity<Vote> vote(@PathVariable Long agendaId, @PathVariable String associetedId, @RequestParam Boolean voto) {
        return ResponseEntity.ok(voteService.vote(agendaId, associetedId, voto));
    }
}

