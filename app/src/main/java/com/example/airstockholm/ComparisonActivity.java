package com.example.airstockholm;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ComparisonActivity extends AppCompatActivity {

    private TextView textTemperatureComparison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        // Initialize TextView
        textTemperatureComparison = findViewById(R.id.textTemperatureComparison);

        // Retrieve current environmental parameters
        String currentTemperature = DataStockage.getInstance().getTemperatureData();
        String currentOzone = DataStockage.getInstance().getO3Data();
        String currentAQI = DataStockage.getInstance().getAqiData();
        String currentPM25 = DataStockage.getInstance().getPm25Data();


        // Convert data to integers for comparison
        double currentTemperatureInt = Double.parseDouble(currentTemperature);
        int currentOzoneInt = Integer.parseInt(currentOzone);
        int currentAQIInt = Integer.parseInt(currentAQI);
        int currentPM25Int = Integer.parseInt(currentPM25);

        // Perform various comparisons and update UI
        stockholmAQI(currentAQIInt);
        compareTemperature(currentTemperatureInt);
        compareOzone(currentOzoneInt);
        maskAlert(currentAQIInt);
        comparePM25Cigarette(currentPM25Int);


    }

    // Method to provide Stockholm AQI information
    private void stockholmAQI(int currentAQI) {
        String aqiStockholm;
        if (currentAQI == 5) {
            aqiStockholm = "Usually Stockholm has good air quality, today : Very Poor";
        } else if (currentAQI == 4) {
            aqiStockholm = "Usually Stockholm has good air quality, today : Poor";
        } else if (currentAQI == 3) {
            aqiStockholm = "Usually Stockholm has good air quality, today : Moderate";
        } else if (currentAQI == 2) {
            aqiStockholm = "Usually Stockholm has good air quality, today : Fair";
        } else {
            aqiStockholm = "Usually Stockholm has good air quality, today : Good";
        }
        TextView textStockholmAQI = findViewById(R.id.textStockholmAQI);
        textStockholmAQI.setText(aqiStockholm);
    }

    // Method to compare current temperature with the monthly average
    private void compareTemperature(Double currentTemperature) {
        int month = getCurrentMonth();
        double averageMonthTemperature = DataTemperature.getAverageTemperature(month);

        double temperatureDifference = currentTemperature - averageMonthTemperature;

        String comparisonText;
        if (temperatureDifference > 0) {
            comparisonText = "Temperature Difference: +" + temperatureDifference + " °C \nHigher than Monthly Average";
        } else if (temperatureDifference < 0) {
            comparisonText = "Temperature Difference: " + temperatureDifference + " °C \nLower than Monthly Average";
        } else {
            comparisonText = "Temperature Difference: 0 °C \nEqual to Monthly Average";
        }

        TextView temperatureText = findViewById(R.id.textTemperatureComparison);
        temperatureText.setText(comparisonText);
    }

    // Method to compare current ozone level
    private void compareOzone(int currentOzone) {
        String ozoneLevelText;
        if (currentOzone > 140) {
            ozoneLevelText = "Ozone Level: High (" + currentOzone + ")";
        } else if (currentOzone >= 60) {
            ozoneLevelText = "Ozone Level: Moderate (" + currentOzone + ")";
        } else {
            ozoneLevelText = "Ozone Level: Low (" + currentOzone + ")";
        }

        TextView textOzoneLevel = findViewById(R.id.textOzoneLevel);
        textOzoneLevel.setText(ozoneLevelText);
    }

    // Method to provide mask alert based on AQI
    private void maskAlert(int currentAQI) {
        String maskAlert;
        if (currentAQI == 5) {
            maskAlert = "You should wear a mask to go outside";
        } else if (currentAQI == 4) {
            maskAlert = "Wearing a mask is advised to go outside";
        } else {
            maskAlert = "No mask needed to go outside";
        }

        TextView textMask = findViewById(R.id.textMaskAdvice);
        textMask.setText(maskAlert);
    }

    // Method to compare current PM2.5 level with cigarettes
    private void comparePM25Cigarette(int currentPM25) {
        double cigarettesPerUnitPM25 = 1.0 / 22.0;
        double equivalentCigarettes = currentPM25 * cigarettesPerUnitPM25;
        equivalentCigarettes = Math.round(equivalentCigarettes * 10.0) / 10.0;

        String pm25Cigarette = "The quantity of PM2.5 is the equivalent of smoking " + equivalentCigarettes +
                " cigarettes a day (" + currentPM25 + ")";

        TextView textPM25 = findViewById(R.id.textPM25);
        textPM25.setText(pm25Cigarette);
    }

    // Method to get the current month
    private int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }
}