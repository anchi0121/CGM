package com.work.easystep2.model;
	

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonInclude(Include.NON_NULL)
public class TravelerSearchResultDTO extends SearchResultDTO<Traveler> {
	
	@JsonProperty("travelerId") 
	private Integer travelerId;
	
    @JsonProperty("travelerAcct")
    private String travelerAcct;

    @JsonProperty("travelerName")
    private String travelerName;

    @JsonProperty("sexAbbr")
    private String sexAbbr;
    
    @JsonProperty("mobile")
    private String mobile;

    public TravelerSearchResultDTO(Traveler data) {
        super(data);
        this.travelerId = data.getTravelerid();
        this.travelerAcct = data.getAcct();
        this.travelerName = data.getUsername();
        this.sexAbbr = data.getSexabbr();
        this.mobile = data.getMobile();
    }

 
    
    public String getMobile() {
		return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public Integer getTravelerId() {
        return travelerId;
    }

    public void setTravelerId(Integer travelerId) {
        this.travelerId = travelerId;
    }
    
    

    public String getTravelerAcct() {
        return travelerAcct;
    }

    public void setTravelerAcct(String travelerAcct) {
        this.travelerAcct = travelerAcct;
    }

    public String getTravelerName() {
        return travelerName;
    }

    public void setTravelerName(String travelerName) {
        this.travelerName = travelerName;
    }

    public String getSexAbbr() {
        return sexAbbr;
    }

    public void setSexAbbr(String sexAbbr) {
        this.sexAbbr = sexAbbr;
    }

    @Override
    public String toString() {
        return super.toString() + ", [travelerAcct=" + travelerAcct + ", travelerName=" + travelerName + ", sexAbbr=" + sexAbbr + " travelerId=" + travelerId + "mobile=" + mobile +"]";
    }
}