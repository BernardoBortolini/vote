package com.challenge.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "voting")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private LocalDateTime openingDate;
    private LocalDateTime closedDate;

    @ManyToOne
    private Agenda agenda;
}
