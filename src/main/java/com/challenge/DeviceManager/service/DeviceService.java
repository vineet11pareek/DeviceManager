package com.challenge.DeviceManager.service;

import com.challenge.DeviceManager.dto.DeviceRequest;
import com.challenge.DeviceManager.dto.DeviceResponse;
import com.challenge.DeviceManager.mapper.DeviceMapper;
import com.challenge.DeviceManager.model.Device;
import com.challenge.DeviceManager.model.DeviceState;
import com.challenge.DeviceManager.repository.DeviceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DeviceService {
    @Autowired
    DeviceRepository deviceRepo;

    public String addDevice(DeviceRequest deviceRequest) {
        var device = DeviceMapper.pojoFromRequest(deviceRequest);
        return deviceRepo.save(device).getId();
    }

    public List<DeviceResponse> getAllDevices() {
        var deviceList = deviceRepo.findAll();
        var list = deviceList.stream()
                .map(DeviceMapper :: pojoToResponseDTO)
                .toList();
        return list;

    }

    public DeviceResponse updateDeviceById(String deviceId, DeviceRequest deviceRequest) {

        var device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        if (device.getState() == DeviceState.IN_USE) {
            throw new RuntimeException("Device cannot be updated");
        }
        DeviceMapper.updateRequestDTOToPojo(deviceRequest,device);
        var saved = deviceRepo.save(device);

        return DeviceMapper.pojoToResponseDTO(saved);
    }

    public String changeState(String deviceId, String state) {
        var device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        if(state !=null && !state.isEmpty() && !device.getState().equals(state.toUpperCase())){
            if(state.equalsIgnoreCase(DeviceState.IN_USE.toString()))
                device.setState(DeviceState.IN_USE);
            if(state.equalsIgnoreCase(DeviceState.AVAILABLE.toString()))
                device.setState(DeviceState.AVAILABLE);
            if(state.equalsIgnoreCase(DeviceState.INACTIVE.toString()))
                device.setState(DeviceState.INACTIVE);

            return deviceRepo.save(device).getId();
        }
        return "Device state not changed";
    }

    public DeviceResponse getDeviceById(String deviceId) {
        var device = deviceRepo.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        return DeviceMapper.pojoToResponseDTO(device);
    }

    public List<DeviceResponse> getDeviceByBrand(String brand) {
        var deviceList = deviceRepo.findByBrandIgnoreCase(brand);
        var list = deviceList.stream()
                .map(DeviceMapper::pojoToResponseDTO)
                .toList();
        return list;
    }

    public List<DeviceResponse> getDeviceByState(DeviceState state) {
        var deviceList = deviceRepo.findByState(state);
        var list = deviceList.stream()
                .map(DeviceMapper::pojoToResponseDTO)
                .toList();
        return list;
    }

    public boolean deleteById(String deviceId) {
        try {
            var device = deviceRepo.findById(deviceId).orElse(null);
            if(device !=null && !device.getState().equals(DeviceState.IN_USE)){
                deviceRepo.deleteById(deviceId);
                return true;
            }


        }catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;

    }
}
