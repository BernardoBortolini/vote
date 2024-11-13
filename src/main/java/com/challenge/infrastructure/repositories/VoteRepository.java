package com.challenge.infrastructure.repositories;

import com.challenge.domain.entities.Agenda;
import com.challenge.domain.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByAgendaAndAssociateId(Agenda agenda, String associateId);
}
