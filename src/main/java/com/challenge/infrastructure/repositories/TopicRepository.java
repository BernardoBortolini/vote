package com.challenge.infrastructure.repositories;

import com.challenge.domain.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
}
