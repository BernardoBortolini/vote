package com.challenge.domain.services;

import com.challenge.domain.entities.Agenda;
import com.challenge.domain.entities.VotingSession;
import com.challenge.infrastructure.repositories.AgendaRepository;
import com.challenge.infrastructure.repositories.VotingSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VotingSessionService {
    @Autowired
    private VotingSessionRepository votingSessionRepository;
    @Autowired
    private AgendaRepository agendaRepository;

    public VotingSession openSession(Long pautaId, int duracaoMinutos) {
        Agenda agenda = agendaRepository.findById(pautaId)
                .orElseThrow(() -> new EntityNotFoundException("Agenda not found"));

        VotingSession session = new VotingSession();
        session.setAgenda(agenda);
        session.setOpeningDate(LocalDateTime.now());
        session.setClosedDate(LocalDateTime.now().plusMinutes(duracaoMinutos));

        return votingSessionRepository.save(session);
    }

}
