package com.challenge.devicemanager.repository;

import com.challenge.devicemanager.model.Device;
import com.challenge.devicemanager.model.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
    List<Device> findByBrandIgnoreCase(String brand);
    List<Device> findByState(DeviceState state);

}
