package com.work.easystep2.REPO;


import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.Photo;

public interface PhotoReopsitory extends JpaRepository<Photo, Integer> {
	

}
