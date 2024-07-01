package com.work.easystep2.service;

import java.text.DecimalFormat;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.work.easystep2.REPO.CGMDeviceRepository;
import com.work.easystep2.REPO.CGMRepository;
import com.work.easystep2.model.CGM;
import com.work.easystep2.model.CGMDevice;

import jakarta.transaction.Transactional;

@Service
public class CGMService {

    @Autowired
    private CGMRepository CGMRepo;
    @Autowired
    private CGMDeviceRepository deviceRepo;
    
    private final DecimalFormat df = new DecimalFormat("#.#");

    private final Random random = new Random();
    
    // 設置為每五分鐘執行一次
    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void generateGlucoseData() {
        CGM lastRecord = CGMRepo.findTopByOrderByTimestampDesc();
        double lastValue = (lastRecord != null) ? lastRecord.getBloodSugar() : 0;
        double value = simulatePeriodicGlucoseVariation(lastValue);

        // Ensure value is within a reasonable range
        value = Math.max(1, Math.min(100, value));

        CGM cgm = new CGM();
        cgm.setBloodSugar(value);
        cgm.setTimestamp(ZonedDateTime.now());
        cgm.setCGMDevice(generateRandomDevice());
        CGMRepo.save(cgm);

        System.out.println("Generated blood sugar value: " + value);
    }

    private double simulatePeriodicGlucoseVariation(double lastValue) {
        // Define the pattern as 10 20 30 40 50 60 70 80 90 80 70 60 50 40 30 20 10 ...
        int period = 10;
        int amplitude = 80;
        int offset = 10;

        // Calculate the position in the pattern
        int position = ((int) (System.currentTimeMillis() / 1000)) % (2 * period);
        if (position > period) {
            position = 2 * period - position;
        }
        double value = position * amplitude / period + offset;

        return Double.parseDouble(df.format(value));
    }
    private CGMDevice generateRandomDevice() {
        List<CGMDevice> devices = deviceRepo.findAll();
        int index = random.nextInt(devices.size());
        return devices.get(index);
    }
}