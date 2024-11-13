package com.challenge.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "agenda")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createDate = LocalDateTime.now();
}
