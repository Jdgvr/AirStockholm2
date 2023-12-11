package com.example.airstockholm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DataComparison extends AppCompatActivity{

    private TextView textTemperatureComparison;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison);

        textTemperatureComparison = findViewById(R.id.textTemperatureComparison);

        int currentTemperature = DataTemperature.generateRandomTemperature();
        compareTemperature(currentTemperature);
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
        textTemperatureComparison.append("\n" + comparisonText);
    }

    private int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH);
    }

}
