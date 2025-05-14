package com.yarmak.neoHelper.model.score;

import com.yarmak.neoHelper.model.patient.Patient;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "scoreresults")
public class ScoreResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_result_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "score_id", nullable = false)
    private Score score;

    @Column(name = "values_results", columnDefinition = "JSON")
    private String valuesResults; // JSON с результатами

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "total_score")
    private Integer totalScore;
}