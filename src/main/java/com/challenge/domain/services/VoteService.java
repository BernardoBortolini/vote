package com.challenge.domain.services;

import com.challenge.domain.entities.Agenda;
import com.challenge.domain.entities.Vote;
import com.challenge.infrastructure.repositories.AgendaRepository;
import com.challenge.infrastructure.repositories.VoteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AgendaRepository agendaRepository;

    public Vote vote(Long pautaId, String associadoId, Boolean voto) {
        Agenda agenda = agendaRepository.findById(pautaId)
                .orElseThrow(() -> new EntityNotFoundException("Pauta não encontrada"));

        if (voteRepository.findByAgendaAndAssociateId(agenda, associadoId).isPresent()) {
            throw new IllegalStateException("Associado já votou nesta pauta.");
        }

        Vote newVote = new Vote();
        newVote.setAgenda(agenda);
        newVote.setAssociateId(associadoId);
        newVote.setVoted(voto);

        return voteRepository.save(newVote);
    }
}
