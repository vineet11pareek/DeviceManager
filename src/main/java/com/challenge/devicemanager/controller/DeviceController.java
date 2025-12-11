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

    /**
     * Fetch all available devices.
     *
     * @return List of devices wrapped in HTTP response.
     */
    @GetMapping
    public ResponseEntity<List<DeviceResponse>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    /**
     * Create a new device.
     *
     * @param deviceRequest Request body containing device details.
     * @return Successful add message with generated device ID.
     */
    @PostMapping("/add")
    public ResponseEntity<String> addDevice(@Valid @RequestBody DeviceRequest deviceRequest) {
        return ResponseEntity.ok("Device Added successfully " + deviceService.addDevice(deviceRequest));
    }

    /**
     * Update an existing device by ID.
     *
     * Business rules validated here:
     * - Creation time cannot be updated.
     * - Name and brand cannot be updated if device is in use
     * @param deviceRequest Request body containing updated device details.
     * @param deviceId Device ID that need to be updated.
     * @return Created device.
     */
    @PutMapping("/update/{deviceId}")
    public ResponseEntity<DeviceResponse> updateDevice(@Valid @RequestBody DeviceRequest deviceRequest, @PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.updateDeviceById(deviceId, deviceRequest));
    }

    /**
     * Fetch single device from device ID.
     *
     * @param deviceId Device ID that need to be updated.
     * @return Device wrapped in HTTP response.
     */
    @GetMapping("/getById/{deviceId}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable String deviceId) {
        return ResponseEntity.ok(deviceService.getDeviceById(deviceId));
    }

    /**
     * Fetch all available devices with same Brands.
     *
     * @param brand Brand name that required to fetch all devices with same brand.
     * @return List of devices wrapped in HTTP response.
     */
    @GetMapping("/getByBrand/{brand}")
    public ResponseEntity<List<DeviceResponse>> getDeviceByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(deviceService.getDeviceByBrand(brand));
    }

    /**
     * Fetch all available devices with device state.
     *
     * @param state State that required to fetch all devices that under in that state.
     * @return List of devices wrapped in HTTP response.
     */
    @GetMapping("/getByState/{state}")
    public ResponseEntity<List<DeviceResponse>> getDeviceByState(@PathVariable DeviceState state) {
        return ResponseEntity.ok(deviceService.getDeviceByState(state));
    }

    /**
     * Delete an existing device by ID.
     *
     * Business rules validated here:
     * - In-use devices cannot be deleted.
     * @param deviceId Device ID that need to be deleted.
     * @return ID of deleted device.
     */
    @DeleteMapping("/delete/{deviceId}")
    public ResponseEntity<String> deleteDevice(@PathVariable String deviceId) {
        return ResponseEntity.ok("Device deleted successfully " + deviceService.deleteById(deviceId));
    }

    /**
     * Change the device state.
     *
     * @param deviceId Device ID that state need to be changed.
     * @param state new device state that need to be changed.
     * @return ID of updated state device.
     */
    @PostMapping("/changeState/{deviceId}")
    public ResponseEntity<String> getDeviceById(@PathVariable String deviceId, @RequestBody String state) {
        return ResponseEntity.ok(deviceService.changeState(deviceId, state));
    }

}
