package com.work.easystep2.model;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CGMDevice")
@Getter
@Setter
public class CGMDevice {

@Id
@Column(name = "DeviceId")
private UUID deviceId;

@Column(name = "DeviceName")
private String deviceName;

public CGMDevice() {
}

public CGMDevice(UUID deviceId, String deviceName) {
    this.deviceId = deviceId;
    this.deviceName = deviceName;
}
}
