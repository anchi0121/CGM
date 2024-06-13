package com.work.easystep2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.easystep2.REPO.CGMRepository;
import com.work.easystep2.model.CGM;

@RestController
@CrossOrigin
public class CGMController {
	  @Autowired
	    private CGMRepository CGMRepo;

	    @GetMapping("/CGM")
	    public List<CGM> getCGMData() {
	        return CGMRepo.findAll();
	    }
}
