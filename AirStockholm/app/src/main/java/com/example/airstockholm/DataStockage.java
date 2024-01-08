package com.example.airstockholm;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// this class provides an object to have access to the data retrievet through the API.
// This way, it is not needed to call the server everytime.
// The values are updated everytime the MainActivity is called
public class DataStockage {

    private static DataStockage instance;
    private Map<String, Double> sensors_values_map;

    public static synchronized DataStockage getInstance() {
        if (instance == null) {
            instance = new DataStockage();
        }
        return instance;
    }

    private void setMap(JSONObject json) throws JSONException {
        Map<String, Double> sensors_values_map = new HashMap<>();
        Iterator<String> keys = json.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            System.out.print(key);
            Double value = (Double) json.get(key);
            sensors_values_map.put(key, value);
        }
        this.sensors_values_map = sensors_values_map;
    }

    // to set the values
    public void setSensors_values(JSONObject json) throws JSONException {
        setMap(json);
    }

    // to get the values from the hashmap
    public int getTemperature(){
        return (int) Math.round(sensors_values_map.get("T"));
    }
    public int getAQI(){
        return (int) Math.round(sensors_values_map.get("AQI"));
    }
    public int getAQI_P(){
        return (int) Math.round(sensors_values_map.get("AQI_P"));
    }

    // these three functions above are held separately to avoid the conversion between double and int in the activities

    public double getFeatureValue(String key){
        return sensors_values_map.get(key);
    }
    // possible keys to pass are aqi, aqi_p, t, co, no2, no, pm10, pm25, nh3, o3, so2
}


