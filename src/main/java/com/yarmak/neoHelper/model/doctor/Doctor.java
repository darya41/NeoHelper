package com.yarmak.neoHelper.model.doctor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctors")
public class Doctor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int doctorId;

	@NonNull
	
	@Column(name = "first_name", nullable = false, length = 45)
	private String firstName;

	@NonNull
	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "patronymic", nullable = true)
	private String patronymic;

	@NonNull
	@Column(name = "login", nullable = false)
	private String login;

	@NonNull
	@Column(name = "password", nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "specialization_id", nullable = false)
	private Specialization specialization;

	@Column(name = "work_phone")
	private String workPhone;

	@Column(name = "personal_phone")
	private String personalPhone;

	@Column(name = "work_email")
	private String workEmail;

	@Column(name = "department_floor")
	private Integer departmentFloor;

	@Column(name = "department_room")
	private String departmentRoom;

	private boolean termsAccepted;

}
