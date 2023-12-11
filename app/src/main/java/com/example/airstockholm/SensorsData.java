package com.example.airstockholm;

public class SensorsData {

    private String attributes;
    private int currentData;
    private String safeLevel;

    public SensorsData(String attributes, int currentData, String safeLevel) {
        this.attributes = attributes;
        this.currentData = currentData;
        this.safeLevel = safeLevel;
    }
    public String getAttributes() {
        return attributes;
    }
    public int getCurrentData() {
        return currentData;
    }
    public String getSafeLevel() {
        return safeLevel;
    }
}
