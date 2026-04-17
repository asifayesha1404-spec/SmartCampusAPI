/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ayesha.api.smartcampusapi;

import java.util.ArrayList;
import java.util.List;

public class Sensor {

    private int id;
    private String type;
    private double currentValue;
    private int roomId;

    // ✅ NEW: status field (for 403 logic)
    private String status;

    // ✅ readings list (for Part 4)
    private List<Double> readings = new ArrayList<>();

    // ✅ Default constructor (IMPORTANT for JSON)
    public Sensor() {}

    // ✅ Full constructor
    public Sensor(int id, String type, double currentValue, int roomId, String status) {
        this.id = id;
        this.type = type;
        this.currentValue = currentValue;
        this.roomId = roomId;
        this.status = status;
    }

    // ✅ Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    // ✅ NEW: status getter/setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ✅ Readings (Part 4)
    public List<Double> getReadings() {
        return readings;
    }

    public void setReadings(List<Double> readings) {
        this.readings = readings;
    }
}