package com.challenge.DeviceManager.dto;

import com.challenge.DeviceManager.model.DeviceState;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record DeviceRequest(@NotBlank String name, @NotBlank String brand) {

}
