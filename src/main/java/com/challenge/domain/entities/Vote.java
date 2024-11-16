package com.challenge.domain.entities;

import com.challenge.domain.constants.VoteTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vote {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // utilizando sequence para poder salvar em batch
    private Long id;
    private Long topicId;
    private Long associateId;

    @Enumerated(EnumType.STRING)
    private VoteTypeEnum vote;

}

