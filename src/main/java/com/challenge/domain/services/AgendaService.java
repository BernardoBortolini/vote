package com.challenge.domain.services;

import com.challenge.domain.entities.Agenda;
import com.challenge.infrastructure.repositories.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    public Agenda registerAgenda(Agenda agenda) {
        return agendaRepository.save(agenda);
    }
}
