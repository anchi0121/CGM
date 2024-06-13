package com.work.easystep2.REPO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.CGM;


public interface CGMRepository extends JpaRepository<CGM, Long>{

	CGM findTopByOrderByTimestampDesc();

}
