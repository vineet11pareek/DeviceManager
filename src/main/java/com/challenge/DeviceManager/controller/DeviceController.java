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
    public String updateDevice(@RequestParam String deviceId){
        System.out.println(deviceId);
        return "its update device";
    }

    @GetMapping("/getById/{deviceId}")
    public String getDeviceById(@RequestParam String deviceId){
        System.out.println(deviceId);
        return "its get device by ID";
    }

    @GetMapping("/getByBrand/{brand}")
    public String getDeviceByBrand(@RequestParam String state){
        System.out.println(state);
        return "its get device by Brand";
    }

    @GetMapping("/getByState/{state}")
    public String getDeviceByState(@RequestParam String brand){
        System.out.println(brand);
        return "its get device by state";
    }

    @DeleteMapping("/delete/{deviceId}")
    public String deleteDevice(@RequestParam String deviceId){
        System.out.println(deviceId);
        return "its delete device by ID";
    }






}
