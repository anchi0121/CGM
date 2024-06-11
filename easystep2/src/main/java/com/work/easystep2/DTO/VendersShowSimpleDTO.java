package com.work.easystep2.DTO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VendersShowSimpleDTO {

	private Integer venderId;
	
	private String venderName;
	
	private String venderIntroduce;
	
	private String showphoto;
	
	private Date writeOrUpdate;
	
	public VendersShowSimpleDTO() {
		
	}
}
