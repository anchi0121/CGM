package com.work.easystep2.model;


import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name="CGM")
@Getter
@Setter
public class CGM {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "glucoseId")
	private Long GId;
	
	@Column(name = "bloodsugar")
	private double bloodSugar;
	
	@Column(name = "timestamp")
	private ZonedDateTime timestamp;
	
	@ManyToOne
	@JoinColumn(name = "DeviceId", referencedColumnName = "DeviceId")
	private CGMDevice CGMDevice;
	
	public CGM() {
		
	}

	
}
