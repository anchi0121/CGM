package com.work.easystep2.DTO;

import java.time.ZonedDateTime;
import java.util.UUID;

public class CGMpostDTO {
	  private double bloodSugar;
	    private UUID deviceId;
	    private ZonedDateTime timestamp;
	  
	    
	    public CGMpostDTO(double bloodSugar,UUID deviceId,ZonedDateTime timestamp){
	    	 this.bloodSugar = bloodSugar;
	    	 this.deviceId = deviceId;
	         this.timestamp = timestamp;
	    }
	    
	    public CGMpostDTO() {
	    	
	    }
	    public UUID getDeviceId() {
			return deviceId;
		}
		public void setDeviceId(UUID deviceId) {
			this.deviceId = deviceId;
		}
		public double getBloodSugar() {
			return bloodSugar;
		}
		public void setBloodSugar(double bloodSugar) {
			this.bloodSugar = bloodSugar;
		}

		public ZonedDateTime getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(ZonedDateTime timestamp) {
			this.timestamp = timestamp;
		}

}
