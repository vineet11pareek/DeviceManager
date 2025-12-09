package com.challenge.DeviceManager.controller;

import com.challenge.DeviceManager.entity.Device;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @GetMapping
    public List<Device> getAllDevices(){
        return new ArrayList<>();
    }

    @PostMapping("/add")
    public String addDevice(@RequestBody Device device){
        return device.toString();
    }

    @PutMapping("/update/{deviceId}")
    public String updateDevice(@PathVariable String deviceId){
        System.out.println(deviceId);
        return "its update device";
    }

    @GetMapping("/getById/{deviceId}")
    public String getDeviceById(@PathVariable String deviceId){
        System.out.println(deviceId);
        return "its get device by ID";
    }

    @GetMapping("/getByBrand/{brand}")
    public String getDeviceByBrand(@PathVariable String brand){
        System.out.println(brand);
        return "its get device by Brand";
    }

    @GetMapping("/getByState/{state}")
    public String getDeviceByState(@PathVariable String state){
        System.out.println(state);
        return "its get device by state";
    }

    @DeleteMapping("/delete/{deviceId}")
    public String deleteDevice(@PathVariable String deviceId){
        System.out.println(deviceId);
        return "its delete device by ID";
    }






}
