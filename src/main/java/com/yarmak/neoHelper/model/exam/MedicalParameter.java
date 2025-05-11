package com.yarmak.neoHelper.model.exam;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medicalparameters")
public class MedicalParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_parameter_id")
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "value_type", nullable = false)
    private String valueType;

    @Column(name = "unit")
    private String unit;

    @Column(name = "description")
    private String description;
    
    @OneToMany(mappedBy = "medicalParameter", cascade = CascadeType.ALL)
    private List<ParameterValue> parameterValues;


}