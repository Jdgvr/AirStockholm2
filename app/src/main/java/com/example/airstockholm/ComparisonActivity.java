package com.example.airstockholm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ComparisonActivity extends AppCompatActivity{

    private TextView textTemperatureComparison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        textTemperatureComparison = findViewById(R.id.textTemperatureComparison);

        int currentTemperature = Integer.parseInt(DataStockage.getInstance().getTemperatureData());
        int currentOzone = Integer.parseInt(DataStockage.getInstance().getO3Data());
        int currentAQI = Integer.parseInt(DataStockage.getInstance().getAqiData());
        int currentPM25 = Integer.parseInt(DataStockage.getInstance().getPm25Data());

        stockholmAQI(currentAQI);
        compareTemperature(currentTemperature);
        compareOzone(currentOzone);
        maskAlert(currentAQI);
        comparePM25Cigarette(currentPM25);
    }

    private void stockholmAQI(int currentAQI) {
        String aqiStockholm;
        if (currentAQI == 5) {
            aqiStockholm = "Usually Stockholm has good air quality, today : Very Poor";
        } else if (currentAQI == 4) {
            aqiStockholm = "Usually Stockholm has good air quality, today : Poor";
        } else if (currentAQI == 3) {
            aqiStockholm = "Usually Stockholm has good air quality, today : Moderate";
        }else if (currentAQI == 2) {
            aqiStockholm = "Usually Stockholm has good air quality, today : Fair";
        } else {
            aqiStockholm = "Usually Stockholm has good air quality, today : Good";
        }
        TextView textStockholmAQI = findViewById(R.id.textStockholmAQI);
        textStockholmAQI.setText(aqiStockholm);
    }
    private void compareTemperature(int currentTemperature) {

        int month = getCurrentMonth();
        int averageMonthTemperature = DataTemperature.getAverageTemperature(month);

        int temperatureDifference = currentTemperature - averageMonthTemperature;

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

    private void compareOzone(int currentOzone){
        String ozoneLevelText;
        if (currentOzone > 140) {
            ozoneLevelText = "Ozone Level: High ("+ currentOzone + ")";
        } else if (currentOzone >= 60 && currentOzone <= 140) {
            ozoneLevelText = "Ozone Level: Moderate (" + currentOzone + ")";
        } else {
            ozoneLevelText = "Ozone Level: Low (" + currentOzone + ")";
        }

        TextView textOzoneLevel = findViewById(R.id.textOzoneLevel);
        textOzoneLevel.setText(ozoneLevelText);
    }

    private void maskAlert(int currentAQI){
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
    private void comparePM25Cigarette(int currentPM25){

        double cigarettesPerUnitPM25 = 1.0 / 22.0;
        double equivalentCigarettes = currentPM25 * cigarettesPerUnitPM25;
        equivalentCigarettes = Math.round(equivalentCigarettes * 10.0) / 10.0;

        String pm25Cigarette = "The quantity of PM2.5 is the equivalent of smoking " + equivalentCigarettes + " cigarettes a day (" + currentPM25 +")";

        TextView textPM25 = findViewById(R.id.textPM25);
        textPM25.setText(pm25Cigarette);
    }
    private int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

}
