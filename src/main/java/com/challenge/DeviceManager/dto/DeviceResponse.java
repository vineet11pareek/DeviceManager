package com.challenge.DeviceManager.dto;

import com.challenge.DeviceManager.model.DeviceState;

import java.time.LocalDateTime;

public record DeviceResponse(String id, String name, DeviceState state, String brand, LocalDateTime createdAt) {
}
