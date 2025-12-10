package com.challenge.devicemanager.dto;

import com.challenge.devicemanager.model.DeviceState;

import java.time.LocalDateTime;

public record DeviceResponse(String id, String name, DeviceState state, String brand, LocalDateTime createdAt) {
}
