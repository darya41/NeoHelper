package com.yarmak.neoHelper.model.patient;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private int id;

	@Column(name = "address_type", nullable = false)
	private String addressType;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "name_address_type")
	private String nameAddressType;

	@Column(name = "house_number", nullable = false)
	private String houseNumber;

	@Column(name = "apartment")
	private String apartment;

	@Column(name = "building")
	private String building;

	
}
