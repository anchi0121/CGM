package com.work.easystep2.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="vender_information")
public class VenderInformation {
	
	@Id
	private Integer venderId;
	
	@Column(name="vender_Introduce")
	private String venderIntroduce;
	
	@Column(name="vender_workmonth")
	private String venderWorkmonth;
	
	@Column(name="vender_stay")
	private String venderStay;
	
	@Column(name="work_mans")
	private String workMans;
	
	@Column(name="vender_worktime")
	private String venderWorktime;
	
	@Column(name="write_update")
	private Date writeOrUpdate;
	@OneToOne
	@JoinColumn(name="vender_venderid" ,unique = true)
	@MapsId
	private Vender vender;

}
