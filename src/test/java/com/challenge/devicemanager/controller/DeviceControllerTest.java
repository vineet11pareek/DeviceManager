package com.challenge.devicemanager.controller;


import com.challenge.devicemanager.dto.DeviceRequest;
import com.challenge.devicemanager.dto.DeviceResponse;
import com.challenge.devicemanager.exceptions.DeviceInUseException;
import com.challenge.devicemanager.exceptions.DeviceNotFoundException;
import com.challenge.devicemanager.model.DeviceState;
import com.challenge.devicemanager.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper;

    //-----------------Add device------------------//

    @Test
    void shouldCreateDevice() throws Exception {
        DeviceRequest request = new DeviceRequest("Laptop", "Dell");

        Mockito.when(deviceService.addDevice(Mockito.any()))
                .thenReturn("device123");

        mockMvc.perform(post("/api/device/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("Device Added successfully device123"));
    }

    //-----------------get device------------------//

    @Test
    void shouldGetDeviceById() throws Exception {
        DeviceResponse response = new DeviceResponse(
                "device123",
                "Laptop",
                DeviceState.AVAILABLE,
                "Dell",
                LocalDateTime.now()
        );

        Mockito.when(deviceService.getDeviceById("device123"))
                .thenReturn(response);

        mockMvc.perform(get("/api/device/getById/device123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("device123"))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    void shouldGetAllDevices() throws Exception {

        List<DeviceResponse> devices = List.of(
                new DeviceResponse("1", "Laptop", DeviceState.AVAILABLE, "Dell", LocalDateTime.now()),
                new DeviceResponse("2", "Phone", DeviceState.IN_USE, "Apple", LocalDateTime.now())
        );

        Mockito.when(deviceService.getAllDevices())
                .thenReturn(devices);

        mockMvc.perform(get("/api/device"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void shouldGetDeviceByBrand() throws Exception {
        List<DeviceResponse> list = List.of(
                new DeviceResponse("1", "Laptop", DeviceState.AVAILABLE, "Dell", LocalDateTime.now())
        );

        Mockito.when(deviceService.getDeviceByBrand("Dell"))
                .thenReturn(list);

        mockMvc.perform(get("/api/device/getByBrand/Dell"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    void shouldGetDeviceByState() throws Exception {
        List<DeviceResponse> list = List.of(
                new DeviceResponse("1", "Laptop", DeviceState.IN_USE, "Dell", LocalDateTime.now())
        );

        Mockito.when(deviceService.getDeviceByState(DeviceState.IN_USE))
                .thenReturn(list);

        mockMvc.perform(get("/api/device/getByState/IN_USE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].state").value("IN_USE"));
    }

    @Test
    void shouldReturn404WhenDeviceNotFound() throws Exception {
        Mockito.when(deviceService.getDeviceById("invalid-id"))
                .thenThrow(new DeviceNotFoundException("Not found"));

        mockMvc.perform(get("/api/device/getById/invalid-id"))
                .andExpect(status().isNotFound());
    }

    //-----------------Update device------------------//

    @Test
    void shouldUpdateDevice() throws Exception {
        DeviceRequest request = new DeviceRequest("Phone", "Samsung");

        DeviceResponse response = new DeviceResponse(
                "device123",
                "Phone",
                DeviceState.AVAILABLE,
                "Samsung",
                LocalDateTime.now()
        );

        Mockito.when(deviceService.updateDeviceById(Mockito.eq("device123"), Mockito.any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/device/update/device123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("device123"))
                .andExpect(jsonPath("$.name").value("Phone"));
    }

    @Test
    void shouldFailWhenUpdatingNameOrBrandIfDeviceInUse() throws Exception {

        DeviceRequest request = new DeviceRequest("Phone", "Apple");

        Mockito.when(deviceService.updateDeviceById(Mockito.eq("device123"), Mockito.any()))
                .thenThrow(new DeviceInUseException("device123"));

        mockMvc.perform(put("/api/device/update/device123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        result.getResolvedException().getMessage()
                                .contains("device is in use"));
    }

    //-----------------Delete device------------------//

    @Test
    void shouldDeleteDevice() throws Exception {

        Mockito.when(deviceService.deleteById("device123"))
                .thenReturn("device123");

        mockMvc.perform(delete("/api/device/delete/device123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Device deleted successfully device123"));
    }

    @Test
    void shouldFailWhenDeletingInUseDevice() throws Exception {

        Mockito.when(deviceService.deleteById("device123"))
                .thenThrow(new DeviceInUseException("device123"));

        mockMvc.perform(delete("/api/device/delete/device123"))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        result.getResolvedException().getMessage()
                                .contains("device is in use"));
    }
}

