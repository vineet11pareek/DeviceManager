package com.challenge.DeviceManager.exceptions;

public class DeviceInUseException extends RuntimeException{
    public DeviceInUseException(String id) {
        super("Device is in use, " + id);
    }
}
