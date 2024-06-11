package com.work.easystep2.REPO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.TravelerToken;

public interface TravelerTokenRepository extends JpaRepository<TravelerToken, Long>{

	
	TravelerToken findByToken(String token);
	
	
	TravelerToken findByTraveler(Traveler traveler);
	
}
