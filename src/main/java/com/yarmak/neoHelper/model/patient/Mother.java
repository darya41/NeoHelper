package com.yarmak.neoHelper.model.patient;

import java.time.LocalDate;

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
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mothers")
public class Mother {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mother_id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	@NonNull
	@Column(name = "first_name", nullable = false)
	private String firstName;

	@NonNull
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "patronymic")
	private String patronymic;

	@Column(name = "date_of_birth", nullable = false)
	private LocalDate dateOfBirth;

	@Column(name = "number_of_deliveries")
	private Integer numberOfDeliveries;

	@Column(name = "number_of_pregnancies")
	private Integer numberOfPregnancies;

	@Column(name = "medications_during_pregnancy", length = 1000)
	private String medicationsDuringPregnancy;

	@Column(name = "gestational_diabetes")
	private Boolean gestationalDiabetes;

	@Column(name = "preeclampsia")
	private Boolean preeclampsia;

	@Column(name = "groupB_streptococcus_status")
	private String groupBStreptococcusStatus;

}
