package com.work.easystep2.controller;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.work.easystep2.DTO.CGMDTO;
import com.work.easystep2.DTO.CGMpostDTO;
import com.work.easystep2.REPO.CGMDeviceRepository;
import com.work.easystep2.REPO.CGMRepository;
import com.work.easystep2.model.CGM;
import com.work.easystep2.model.CGMDevice;

@RestController
@CrossOrigin
public class CGMController {
	  @Autowired
	    private CGMRepository CGMRepo;
      
	  @Autowired
	  private CGMDeviceRepository DeviceRepo;
	    
	  @GetMapping("/CGM")
	    public List<CGM> getCGMData() {
	        return CGMRepo.findAll();
	    }
	    
	  @GetMapping("/bloodSugar/{deviceId}")
	    public ResponseEntity<List<CGMDTO>> getBloodSugarRecords(
	            @PathVariable("deviceId") UUID deviceId,
	            @RequestParam("start") @DateTimeFormat(pattern = "yyyyMMdd'T'HHmmssX") ZonedDateTime start,
	            @RequestParam("end") @DateTimeFormat(pattern = "yyyyMMdd'T'HHmmssX") ZonedDateTime end) {

	        List<CGM> cgms = CGMRepo.findByCGMDevice_DeviceIdAndTimestampBetween(deviceId, start, end);

	        List<CGMDTO> response = cgms.stream()
	                .map(cgm -> new CGMDTO(cgm.getBloodSugar(), cgm.getTimestamp()))
	                .collect(Collectors.toList());

	        return ResponseEntity.ok(response);
	    }
	    
	    
	    @PostMapping("/bloodsugar/post")
	    public ResponseEntity<Map<String, String>> addBloodSugarRecords(@RequestBody CGMpostDTO cgmpostDTO) {
	    
		        UUID deviceId = cgmpostDTO.getDeviceId();

	            System.out.println("Received deviceId: " + deviceId);
	            
	            // 根据提供的deviceId从数据库中查找对应的CGMDevice实体
	            Optional<CGMDevice> optionalCgmDevice = DeviceRepo.findByDeviceId(deviceId);

	            CGMDevice cgmDevice;
	            if (optionalCgmDevice.isPresent()) {
	             
	                cgmDevice = optionalCgmDevice.get();
	            } else {
	            	
	            	Map<String, String> response = new HashMap<>();
	                response.put("message", "Device not found for deviceId: " + deviceId.toString().toUpperCase());
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	            	
//	                // 如果找不到对应的CGMDevice，创建一个新的设备并命名
//	                cgmDevice = new CGMDevice();
//	                cgmDevice.setDeviceId(deviceId);
//
//	                // 命名新的CGMDevice名称
//	                Integer lastNumber = DeviceRepo.findLastDeviceNumber();
//	                int newNumber = (lastNumber != null) ? lastNumber + 1 : 1;
//	                cgmDevice.setDeviceName("CGM" + newNumber);
//
//	                
//	                cgmDevice = DeviceRepo.save(cgmDevice);
	            }

	           
	            CGM cgm = new CGM();
	            cgm.setBloodSugar(cgmpostDTO.getBloodSugar());
	            cgm.setTimestamp(ZonedDateTime.now());
	            cgm.setCGMDevice(cgmDevice);

	            
	            CGMRepo.save(cgm);

	      
	            Map<String, String> response = new HashMap<>();
	            response.put("UUID", deviceId.toString().toUpperCase());

	            return ResponseEntity.status(HttpStatus.CREATED).body(response);
	        
	       }
	    
	    
}
