package com.challenge.devicemanager.exceptions;

public class DeviceInUseException extends RuntimeException{
    public DeviceInUseException(String id) {
        super("Device is in use, " + id);
    }
}
