package com.yarmak.neoHelper.model.score;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "JSON")
    private String parameters; // JSON строка с параметрами шкалы
}
