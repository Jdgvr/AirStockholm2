package com.example.airstockholm;

public class SensorsData {

    private String attributes;
    private String currentData;
    private String safeLevel;

    public SensorsData(String attributes, String currentData, String safeLevel) {
        this.attributes = attributes;
        this.currentData = currentData;
        this.safeLevel = safeLevel;
    }
    public String getAttributes() {
        return attributes;
    }
    public String getCurrentData() {
        return currentData;
    }
    public String getSafeLevel() {
        return safeLevel;
    }
}
