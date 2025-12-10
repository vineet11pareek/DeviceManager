package com.challenge.devicemanager.service;

import com.challenge.devicemanager.dto.DeviceRequest;
import com.challenge.devicemanager.dto.DeviceResponse;
import com.challenge.devicemanager.exceptions.DeviceInUseException;
import com.challenge.devicemanager.exceptions.DeviceNotFoundException;
import com.challenge.devicemanager.model.Device;
import com.challenge.devicemanager.model.DeviceState;
import com.challenge.devicemanager.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeviceServiceTests {

    @Mock
    private DeviceRepository deviceRepo;

    @InjectMocks
    private DeviceService deviceService;

    private Device device;
    private DeviceRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        device = new Device();
        device.setId("1");
        device.setName("iPhone");
        device.setBrand("Apple");
        device.setState(DeviceState.AVAILABLE);
        device.setCreatedAt(LocalDateTime.now());

        request = new DeviceRequest("iPad", "Apple");
    }

    // ---------- addDevice() ----------

    @Test
    void addDevice_shouldReturnId() {
        when(deviceRepo.save(any(Device.class))).thenReturn(device);

        String id = deviceService.addDevice(request);

        assertEquals("1", id);
        verify(deviceRepo, times(1)).save(any(Device.class));
    }

    // ---------- getAllDevices() ----------

    @Test
    void getAllDevices_shouldReturnList() {
        when(deviceRepo.findAll()).thenReturn(List.of(device));

        List<DeviceResponse> result = deviceService.getAllDevices();

        assertEquals(1, result.size());
        assertEquals("iPhone", result.get(0).name());
    }

    // ---------- updateDeviceById() ----------

    @Test
    void updateDeviceById_shouldUpdateSuccessfully() {
        when(deviceRepo.findById("1")).thenReturn(Optional.of(device));
        when(deviceRepo.save(any(Device.class))).thenReturn(device);

        DeviceResponse response = deviceService.updateDeviceById("1", request);

        assertNotNull(response);
        verify(deviceRepo).save(any(Device.class));
    }

    @Test
    void updateDeviceById_shouldThrowNotFound() {
        when(deviceRepo.findById("1")).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () ->
                deviceService.updateDeviceById("1", request));
    }

    @Test
    void updateDeviceById_shouldThrowInUse() {
        device.setState(DeviceState.IN_USE);
        when(deviceRepo.findById("1")).thenReturn(Optional.of(device));

        assertThrows(DeviceInUseException.class, () ->
                deviceService.updateDeviceById("1", request));
    }

    // ---------- changeState() ----------

    @Test
    void changeState_shouldChangeState() {
        when(deviceRepo.findById("1")).thenReturn(Optional.of(device));
        when(deviceRepo.save(any(Device.class))).thenReturn(device);

        String result = deviceService.changeState("1", "IN_USE");

        assertEquals("1", result);
    }

    @Test
    void changeState_shouldThrowNotFound() {
        when(deviceRepo.findById("1")).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () ->
                deviceService.changeState("1", "AVAILABLE"));
    }

    // ---------- getDeviceById() ----------

    @Test
    void getDeviceById_shouldReturnDevice() {
        when(deviceRepo.findById("1")).thenReturn(Optional.of(device));

        DeviceResponse response = deviceService.getDeviceById("1");

        assertEquals("iPhone", response.name());
    }

    @Test
    void getDeviceById_shouldThrowNotFound() {
        when(deviceRepo.findById("1")).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () ->
                deviceService.getDeviceById("1"));
    }

    // ---------- getDeviceByBrand() ----------

    @Test
    void getDeviceByBrand_shouldReturnList() {
        when(deviceRepo.findByBrandIgnoreCase("Apple"))
                .thenReturn(List.of(device));

        List<DeviceResponse> result = deviceService.getDeviceByBrand("Apple");

        assertEquals(1, result.size());
    }

    // ---------- getDeviceByState() ----------

    @Test
    void getDeviceByState_shouldReturnList() {
        when(deviceRepo.findByState(DeviceState.AVAILABLE))
                .thenReturn(List.of(device));

        List<DeviceResponse> result = deviceService.getDeviceByState(DeviceState.AVAILABLE);

        assertEquals(1, result.size());
    }

    // ---------- deleteById() ----------

    @Test
    void deleteById_shouldDeleteSuccessfully() {
        when(deviceRepo.findById("1")).thenReturn(Optional.of(device));

        String result = deviceService.deleteById("1");

        assertEquals("1", result);
        verify(deviceRepo).deleteById("1");
    }

    @Test
    void deleteById_shouldThrowInUse() {
        device.setState(DeviceState.IN_USE);
        when(deviceRepo.findById("1")).thenReturn(Optional.of(device));

        assertThrows(DeviceInUseException.class, () ->
                deviceService.deleteById("1"));
    }

    @Test
    void deleteById_shouldThrowNotFound() {
        when(deviceRepo.findById("1")).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () ->
                deviceService.deleteById("1"));
    }
}

