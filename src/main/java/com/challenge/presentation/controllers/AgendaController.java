package com.challenge.presentation.controllers;

import com.challenge.domain.entities.Agenda;
import com.challenge.domain.services.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agendas")
@RequiredArgsConstructor
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @PostMapping
    public ResponseEntity<Agenda> createAgenda(@RequestBody Agenda agenda) {
        Agenda newAgenda = agendaService.registerAgenda(agenda);
        return ResponseEntity.ok(newAgenda);
    }
}
