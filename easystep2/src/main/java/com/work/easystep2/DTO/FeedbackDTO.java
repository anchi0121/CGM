package com.work.easystep2.DTO;

import java.util.Date;

import com.work.easystep2.model.VenderFeedback;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedbackDTO {
	
	private String venderName;
	
	private String travelerName;
	
	private Date writeDate;
	
	private Integer score;
	
	private String feedback;

	public FeedbackDTO(VenderFeedback v) {
		this.setWriteDate(v.getWriteDate());
		this.setScore(v.getScore());
		this.setFeedback(v.getFeedback());
	}
	public FeedbackDTO() {
		
	}
	public String getVenderName() {
		return venderName;
	}
	public void setVenderName(String venderName) {
		this.venderName = venderName;
	}
	public String getTravelerName() {
		return travelerName;
	}
	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}
	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}
