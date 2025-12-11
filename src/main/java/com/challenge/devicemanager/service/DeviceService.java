package com.challenge.devicemanager.service;

import com.challenge.devicemanager.dto.DeviceRequest;
import com.challenge.devicemanager.dto.DeviceResponse;
import com.challenge.devicemanager.exceptions.DeviceInUseException;
import com.challenge.devicemanager.exceptions.DeviceNotFoundException;
import com.challenge.devicemanager.mapper.DeviceMapper;
import com.challenge.devicemanager.model.DeviceState;
import com.challenge.devicemanager.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class DeviceService {
    @Autowired
    DeviceRepository deviceRepo;

    /**
     * Create a new device entry.
     *
     * @param deviceRequest DTO containing user input.
     * @return Create device ID.
     */
    public String addDevice(DeviceRequest deviceRequest) {
        var device = DeviceMapper.pojoFromRequest(deviceRequest);
        return deviceRepo.save(device).getId();
    }

    /**
     * Fetch all devices from the system.
     *
     * @return List of device responses.
     */
    public List<DeviceResponse> getAllDevices() {
        var deviceList = deviceRepo.findAll();
        var list = deviceList.stream()
                .map(DeviceMapper::pojoToResponseDTO)
                .toList();
        return list;

    }

    /**
     * Update a device by ID.
     * Validations:
     * - Cannot modify createdOn timestamp
     * - Cannot update name/brand if device is "IN_USE"
     *
     * @param deviceId Device ID
     * @param deviceRequest Fields to update
     * @return Updated device response
     */
    public DeviceResponse updateDeviceById(String deviceId, DeviceRequest deviceRequest) {

        var device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found"));
        if (device.getState() == DeviceState.IN_USE) {
            throw new DeviceInUseException("Device cannot be updated");
        }
        DeviceMapper.updateRequestDTOToPojo(deviceRequest, device);
        var saved = deviceRepo.save(device);

        return DeviceMapper.pojoToResponseDTO(saved);
    }


    /**
     * Update a device state by ID.
     *
     * @param deviceId Device ID
     * @param state New state to update
     * @return Updated device ID
     */
    public String changeState(String deviceId, String state) {
        var device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found"));
        if (state != null && !state.isEmpty() && !device.getState().equals(state.toUpperCase())) {
            if (state.equalsIgnoreCase(DeviceState.IN_USE.toString()))
                device.setState(DeviceState.IN_USE);
            if (state.equalsIgnoreCase(DeviceState.AVAILABLE.toString()))
                device.setState(DeviceState.AVAILABLE);
            if (state.equalsIgnoreCase(DeviceState.INACTIVE.toString()))
                device.setState(DeviceState.INACTIVE);

            return deviceRepo.save(device).getId();
        }
        throw new DeviceInUseException("Device cannot be updated");
    }

    /**
     * Fetch single devices from the system.
     *
     *
     * @param deviceId Device ID
     * @return device responses.
     */
    public DeviceResponse getDeviceById(String deviceId) {
        var device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new DeviceNotFoundException("Device not found"));
        return DeviceMapper.pojoToResponseDTO(device);
    }

    /**
     * Fetch all devices with same brand from the system.
     *
     *
     * @param brand Brand name.
     * @return List of device responses.
     */
    public List<DeviceResponse> getDeviceByBrand(String brand) {
        var deviceList = deviceRepo.findByBrandIgnoreCase(brand);
        var list = deviceList.stream()
                .map(DeviceMapper::pojoToResponseDTO)
                .toList();
        return list;
    }

    /**
     * Fetch all devices with same state from the system.
     *
     *
     * @param state Brand name.
     * @return List of device responses.
     */
    public List<DeviceResponse> getDeviceByState(DeviceState state) {
        var deviceList = deviceRepo.findByState(state);
        var list = deviceList.stream()
                .map(DeviceMapper::pojoToResponseDTO)
                .toList();
        return list;
    }

    /**
     * Delete a device only if it's current state not "IN_USE".
     *
     * @param deviceId Device ID
     */
    public String deleteById(String deviceId) {
        var device = deviceRepo.findById(deviceId).orElseThrow(() -> new DeviceNotFoundException(deviceId));
        if (device != null && !device.getState().equals(DeviceState.IN_USE)) {
            deviceRepo.deleteById(deviceId);
            return deviceId;
        }
        throw new DeviceInUseException("Device cannot be Deleted");
    }
}
