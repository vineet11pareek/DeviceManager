package com.challenge.DeviceManager.entity;

public class Device {

    private String id;
    private String name;
    private String brand;
    private DeviceState state;

    public Device(String id, String name, String brand, DeviceState state) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public DeviceState getState() {
        return state;
    }

    public void setState(DeviceState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", state=" + state +
                '}';
    }
}
