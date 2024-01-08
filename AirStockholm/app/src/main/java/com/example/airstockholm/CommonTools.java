package com.example.airstockholm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// this class contains methods that are used from more than one class, to avoid redundancy
public class CommonTools {

    // gets the name that corresponds to the AQI level
    public static String getAQIString(int aqi) {
        switch (aqi) {
            case 1: return "Good.";
            case 2: return "Fair.";
            case 3: return "Moderate.";
            case 4: return "Poor.";
            case 5: return "Very Poor.";
            default: return "[no data].";
        }
    }

    // update the date and the temperature (in the bar above the application that is present in every activity)
    public static String updateUI_Date() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH);
        return "Stockholm, " + dateFormat.format(date);
    }

    public static String updateUI_Temperature(){
        int degrees = DataStockage.getInstance().getTemperature();
        return degrees + "°C";
    }

}
