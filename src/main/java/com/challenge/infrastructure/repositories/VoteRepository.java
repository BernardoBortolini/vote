package com.challenge.infrastructure.repositories;

import com.challenge.domain.entities.Topic;
import com.challenge.domain.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByTopicIdAndAssociateId(Long topicId, Long associateId);
    List<Vote> findByTopicId(Long topicId);
}
