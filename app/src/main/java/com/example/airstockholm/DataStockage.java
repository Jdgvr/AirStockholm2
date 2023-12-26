package com.example.airstockholm;

import android.util.Log;

public class DataStockage {

    private static DataStockage instance;
    private String temperatureData;
    private String aqiData;
    private String coData;
    private String noData;
    private String no2Data;
    private String o3Data;
    private String so2Data;
    private String pm25Data;
    private String pm10Data;
    private String nh3Data;
    private String aqiTomorrowData;

    private DataStockage() {
        // Private constructor to prevent instantiation
        Log.i("DataStockage", "Singleton instance created");
    }
    public static synchronized DataStockage getInstance() {
        if (instance == null) {
            instance = new DataStockage();
        }
        return instance;
    }

    public String getTemperatureData() {
        return temperatureData;
    }

    public void setTemperatureData(String temperatureData) {
        this.temperatureData = temperatureData;
        Log.i("DataStockage", "Temperature data set: " + temperatureData);
    }

    public String getAqiData() {
        return aqiData;
    }

    public void setAqiData(String aqiData) {
        this.aqiData = aqiData;
    }

    public String getNh3Data() {
        return nh3Data;
    }

    public void setNh3Data(String nh3Data) {
        this.nh3Data = nh3Data;
    }

    public String getPm10Data() {
        return pm10Data;
    }

    public void setPm10Data(String pm10Data) {
        this.pm10Data = pm10Data;
    }

    public String getPm25Data() {
        return pm25Data;
    }

    public void setPm25Data(String pm25Data) {
        this.pm25Data = pm25Data;
    }

    public String getSo2Data() {
        return so2Data;
    }

    public void setSo2Data(String so2Data) {
        this.so2Data = so2Data;
    }

    public String getO3Data() {
        return o3Data;
    }

    public void setO3Data(String o3Data) {
        this.o3Data = o3Data;
    }

    public String getNo2Data() {
        return no2Data;
    }

    public void setNo2Data(String no2Data) {
        this.no2Data = no2Data;
    }

    public String getNoData() {
        return noData;
    }

    public void setNoData(String noData) {
        this.noData = noData;
    }

    public String getCoData() {
        return coData;
    }

    public void setCoData(String coData) {
        this.coData = coData;
    }
    public String getAqiTomorrowData() {
        return aqiTomorrowData;
    }

    public void setAqiTomorrowData(String aqiTomorrowData) {
        this.aqiTomorrowData = aqiTomorrowData;
    }
}



