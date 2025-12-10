package com.challenge.devicemanager.exceptions;

public class DeviceNotFoundException extends RuntimeException{
    public DeviceNotFoundException(String id) {
        super("Device not found with id: " + id);
    }
}
