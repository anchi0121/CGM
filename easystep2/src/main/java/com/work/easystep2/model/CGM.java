package com.work.easystep2.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Id;

@Entity
@Table(name="CGM")
@Getter
@Setter
public class CGM {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "glucoseId")
	private Long GId;
	
	@Column(name = "glucoseValue")
	private double GValue;
	
	@Column(name = "timestamp")
	private LocalDateTime timestamp;
	
}
