package com.challenge.DeviceManager.controller;

import com.challenge.DeviceManager.dto.DeviceRequest;
import com.challenge.DeviceManager.dto.DeviceResponse;
import com.challenge.DeviceManager.model.Device;
import com.challenge.DeviceManager.model.DeviceState;
import com.challenge.DeviceManager.service.DeviceService;
import jakarta.validation.Valid;
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
        return new ResponseEntity<>("No device found",HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/add")
    public ResponseEntity<String> addDevice(@Valid @RequestBody DeviceRequest device){
        String deviceId = deviceService.addDevice(device);
        if(deviceId !=null && !deviceId.isEmpty()  ){
            return ResponseEntity.ok("Device Added successfully" +deviceId);
        }
        return new ResponseEntity<>("Device not added, Something is not correct",HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/update/{deviceId}")
    public ResponseEntity<?> updateDevice(@Valid @RequestBody DeviceRequest deviceRequest,@PathVariable String deviceId){
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
    public ResponseEntity<?> getDeviceById(@PathVariable String deviceId){
        try{
            return ResponseEntity.ok(deviceService.getDeviceById(deviceId));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>("Device not found",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getByBrand/{brand}")
    public ResponseEntity<?> getDeviceByBrand(@PathVariable String brand){
        try{
            return ResponseEntity.ok(deviceService.getDeviceByBrand(brand));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>("Device not found with provided brand",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getByState/{state}")
    public ResponseEntity<?> getDeviceByState(@PathVariable DeviceState state){
        try{
            return ResponseEntity.ok(deviceService.getDeviceByState(state));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return new ResponseEntity<>("Device not found with provided State",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable String deviceId){
        if(deviceService.deleteById(deviceId)) {
            return new ResponseEntity<String>("Device deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Device not found", HttpStatus.NOT_FOUND);

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
