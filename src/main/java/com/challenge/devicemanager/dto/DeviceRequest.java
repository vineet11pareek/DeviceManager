package com.challenge.devicemanager.dto;

import jakarta.validation.constraints.NotBlank;

public record DeviceRequest(@NotBlank String name, @NotBlank String brand) {

}
