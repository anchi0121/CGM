package com.work.easystep2.REPO;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.CGM;


public interface CGMRepository extends JpaRepository<CGM, Long>{

	CGM findTopByOrderByTimestampDesc();
	   List<CGM> findByCGMDevice_DeviceIdAndTimestampBetween(UUID deviceId, ZonedDateTime start, ZonedDateTime end);
}
