package com.work.easystep2.DTO;

import java.time.ZonedDateTime;


public class CGMDTO {

    private double bloodSugar;
    private ZonedDateTime timestamp;

    public CGMDTO() {
        // 空構造函數
    }

    public CGMDTO(double bloodSugar, ZonedDateTime timestamp) {
        this.bloodSugar = bloodSugar;
        this.timestamp = timestamp;
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