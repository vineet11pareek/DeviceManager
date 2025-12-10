package com.challenge.DeviceManager.repository;

import com.challenge.DeviceManager.model.Device;
import com.challenge.DeviceManager.model.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
    List<Device> findByBrandIgnoreCase(String brand);
    List<Device> findByState(DeviceState state);

}
