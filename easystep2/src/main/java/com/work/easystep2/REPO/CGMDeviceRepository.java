package com.work.easystep2.REPO;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.work.easystep2.model.CGMDevice;

public interface CGMDeviceRepository extends JpaRepository<CGMDevice, UUID>  {

    Optional<CGMDevice> findByDeviceId(UUID deviceId);
    
    @Query(value = "SELECT MAX(CAST(SUBSTRING(deviceName, 4, LEN(deviceName) - 3) AS INT)) FROM CGMDevice", nativeQuery = true)
    Integer findLastDeviceNumber();
}
