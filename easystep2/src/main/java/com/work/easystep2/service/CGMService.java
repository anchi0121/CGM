package com.work.easystep2.service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.work.easystep2.REPO.CGMRepository;
import com.work.easystep2.model.CGM;

@Service
public class CGMService {

	@Autowired
    private CGMRepository CGMRepo;

	 
	    private final DecimalFormat df = new DecimalFormat("#.#");
	
	    
	   
	    @Scheduled(cron = "0/1 * * * * *")
	    public void generateGlucoseData() {
	         CGM lastRecord = CGMRepo.findTopByOrderByTimestampDesc();
	         double lastValue = (lastRecord != null) ? lastRecord.getGValue() : 0;
	         double value = simulateGlucoseVariation(lastValue);

	        CGM cgm = new CGM();
	        cgm.setGValue(value);
	        cgm.setTimestamp(LocalDateTime.now());
	        CGMRepo.save(cgm);

	        System.out.println("Generated blood sugar value: " + value);
	    }
	    private double simulateGlucoseVariation(double lastValue) {
	    	 double newValue;
	    	 double noise = Math.random() * 10 - 5; // 隨機噪聲在 -5 到 5 之間
             
	    	    // 控制生成的數字 1 和 99 的頻率
	    	    if (lastValue == 1 || lastValue == 99) {
	    	        newValue = lastValue + noise;
	    	    } else {
	    	        // 在範圍 20 到 80 之間生成其他數字
	    	        newValue = 2 + Math.random() * 96;
	    	    }

	    	    // 確保生成的血糖值在合理範圍內
	    	    newValue = Math.max(1, Math.min(99, newValue)); // 血糖值範圍在 1 到 99 之間

	    	    return Double.parseDouble(df.format(newValue));
	}
}