package com.work.easystep2.REPO;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.work.easystep2.model.PhotoAlbum;


public interface AlbumRepository extends JpaRepository<PhotoAlbum, Integer> {

	List<PhotoAlbum> findByTravelerId(Integer userId);
	

}
