package com.challenge.devicemanager.mapper;

import com.challenge.devicemanager.dto.DeviceRequest;
import com.challenge.devicemanager.dto.DeviceResponse;
import com.challenge.devicemanager.model.Device;
import com.challenge.devicemanager.model.DeviceState;

public class DeviceMapper {

    private DeviceMapper(){

    }

    public static DeviceResponse pojoToResponseDTO(Device device){
        return new DeviceResponse(device.getId(),device.getName(),device.getState(),device.getBrand(),device.getCreatedAt());
    }

    public static void updateRequestDTOToPojo(DeviceRequest deviceRequest, Device device){
        device.setName(deviceRequest.name());
        device.setBrand(deviceRequest.brand());
    }

    public static Device pojoFromRequest(DeviceRequest deviceRequest) {
        Device device = new Device();
        device.setName(deviceRequest.name());
        device.setBrand(deviceRequest.brand());
        device.setState(DeviceState.AVAILABLE);
        return device;
    }


}
