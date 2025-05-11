package com.yarmak.neoHelper.model.exam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medparaminpatientexams")
public class MedParamInPatientExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "med_patient_exam_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patients_exams_id")
    private PatientsExam patientsExam;

    @ManyToOne
    @JoinColumn(name = "med_param_exam_id")
    private MedParamInExam medParamExam;

    @Column(name = "value", nullable = false)
    private String value;
}