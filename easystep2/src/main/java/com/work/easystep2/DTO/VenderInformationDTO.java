package com.work.easystep2.DTO;

import com.work.easystep2.model.VenderInformation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenderInformationDTO {

	private String venderIntroduce;

	private String venderWorkmonth;

	private String venderStay;

	private String workMans;

	private String venderWorktime;
	
	public VenderInformationDTO() {
		
	}
	public VenderInformationDTO(VenderInformation vi) {
		this.setVenderIntroduce(vi.getVenderIntroduce());
		this.setVenderWorkmonth(vi.getVenderWorkmonth());
		this.setVenderStay(vi.getVenderStay());
		this.setWorkMans(vi.getWorkMans());
		this.setVenderWorktime(vi.getVenderWorktime());
	}
	public String getVenderIntroduce() {
		return venderIntroduce;
	}
	public void setVenderIntroduce(String venderIntroduce) {
		this.venderIntroduce = venderIntroduce;
	}
	public String getVenderWorkmonth() {
		return venderWorkmonth;
	}
	public void setVenderWorkmonth(String venderWorkmonth) {
		this.venderWorkmonth = venderWorkmonth;
	}
	public String getVenderStay() {
		return venderStay;
	}
	public void setVenderStay(String venderStay) {
		this.venderStay = venderStay;
	}
	public String getWorkMans() {
		return workMans;
	}
	public void setWorkMans(String workMans) {
		this.workMans = workMans;
	}
	public String getVenderWorktime() {
		return venderWorktime;
	}
	public void setVenderWorktime(String venderWorktime) {
		this.venderWorktime = venderWorktime;
	}
}
