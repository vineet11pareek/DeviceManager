package com.challenge.DeviceManager.controller;

import com.challenge.DeviceManager.dto.DeviceRequest;
import com.challenge.DeviceManager.dto.DeviceResponse;
import com.challenge.DeviceManager.model.Device;
import com.challenge.DeviceManager.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/device")
@Slf4j
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public ResponseEntity<?> getAllDevices(){
        List<DeviceResponse> deviceList = deviceService.getAllDevices();
        if(deviceList !=null && !deviceList.isEmpty()){
            return ResponseEntity.ok(deviceList);
        }
        return ResponseEntity
                .badRequest()
                .body("No devices found");

    }

    @PostMapping("/add")
    public ResponseEntity<String> addDevice(@RequestBody DeviceRequest device){
        String deviceId = deviceService.addDevice(device);
        if(deviceId !=null && !deviceId.isEmpty()  ){
            return ResponseEntity.ok(deviceService.addDevice(device));
        }
        return new ResponseEntity<>("Device not added, Something is not correct",HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/update/{deviceId}")
    public ResponseEntity<?> updateDevice(@RequestBody DeviceRequest deviceRequest,@PathVariable String deviceId){
        try{
            return ResponseEntity.ok(deviceService.updateDeviceById(deviceId,deviceRequest));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>("Device not updated, either device in use or not available",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getById/{deviceId}")
    public ResponseEntity<DeviceRequest> getDeviceById(@PathVariable String deviceId){
        System.out.println(deviceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getByBrand/{brand}")
    public ResponseEntity<DeviceRequest> getDeviceByBrand(@PathVariable String brand){
        System.out.println(brand);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getByState/{state}")
    public ResponseEntity<DeviceRequest> getDeviceByState(@PathVariable String state){
        System.out.println(state);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{deviceId}")
    public String deleteDevice(@PathVariable String deviceId){
        System.out.println(deviceId);
        return "its delete device by ID";
    }

    @PostMapping("/changeState/{deviceId}")
    public ResponseEntity<String> getDeviceById(@PathVariable String deviceId,@RequestBody String state){
        try{
            return ResponseEntity.ok(deviceService.changeState(deviceId,state));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>("State not changed",HttpStatus.NOT_MODIFIED);
    }

}
