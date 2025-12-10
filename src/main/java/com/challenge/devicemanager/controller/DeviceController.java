package com.challenge.devicemanager.controller;

import com.challenge.devicemanager.dto.DeviceRequest;
import com.challenge.devicemanager.dto.DeviceResponse;
import com.challenge.devicemanager.model.DeviceState;
import com.challenge.devicemanager.service.DeviceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/device")
@Slf4j
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @PostMapping("/add")
    public ResponseEntity<String> addDevice(@Valid @RequestBody DeviceRequest device) {
        return ResponseEntity.ok("Device Added successfully " + deviceService.addDevice(device));

    }

    @PutMapping("/update/{deviceId}")
    public ResponseEntity<DeviceResponse> updateDevice(@Valid @RequestBody DeviceRequest deviceRequest, @PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.updateDeviceById(deviceId, deviceRequest));
    }

    @GetMapping("/getById/{deviceId}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceById(deviceId));
    }

    @GetMapping("/getByBrand/{brand}")
    public ResponseEntity<List<DeviceResponse>> getDeviceByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(deviceService.getDeviceByBrand(brand));
    }

    @GetMapping("/getByState/{state}")
    public ResponseEntity<List<DeviceResponse>> getDeviceByState(@PathVariable DeviceState state) {
        return ResponseEntity.ok(deviceService.getDeviceByState(state));
    }

    @DeleteMapping("/delete/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable String deviceId) {
        return ResponseEntity.ok("Device deleted successfully " + deviceService.deleteById(deviceId));
    }

    @PostMapping("/changeState/{deviceId}")
    public ResponseEntity<String> getDeviceById(@PathVariable String deviceId, @RequestBody String state) {
        return ResponseEntity.ok(deviceService.changeState(deviceId, state));
    }

}
