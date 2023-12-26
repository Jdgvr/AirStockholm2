package com.example.airstockholm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView title;
    private TextView temperature;
    private TextView temperatureLevel;
    private TextView aqi;
    private TextView aqiResult;
    private TextView todayDate;
    private TextView tomorrowAQI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        title = findViewById(R.id.textViewTitle);
        todayDate = findViewById(R.id.textDate);
        temperature = findViewById(R.id.textViewTemperatureLabel);
        temperatureLevel = findViewById(R.id.textViewTemperature);
        aqi = findViewById(R.id.textViewAqiLabel);
        aqiResult = findViewById(R.id.textViewAqi);
        tomorrowAQI = findViewById(R.id.textViewAqiTomorrow);

        // Display current date
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH);
        String formattedDate = dateFormat.format(currentDate);
        todayDate.setText(formattedDate);

        // Execute HTTP request to fetch data
        new Http(temperatureLevel, aqiResult, null, null, null, null, null, null, null, null, tomorrowAQI) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                // Retrieve and display temperature data
                String temperatureData = DataStockage.getInstance().getTemperatureData();
                temperatureLevel.setText(String.valueOf(temperatureData));

                // Retrieve and display AQI data
                String aqiData = DataStockage.getInstance().getAqiData();
                aqiResult.setText(getAqiLabel(aqiData));

                // Retrieve and display tomorrow's AQI data
                String aqiTomorrowData = DataStockage.getInstance().getAqiTomorrowData();
                tomorrowAQI.setText(getAqiLabel(aqiTomorrowData));
            }
        }.execute();

        // Retrieve and display temperature data
        String temperatureData = DataStockage.getInstance().getTemperatureData();
        temperatureLevel.setText(String.valueOf(temperatureData));

        // Retrieve and display AQI data
        String aqiData = DataStockage.getInstance().getAqiData();
        aqiResult.setText(getAqiLabel(aqiData));

        // Retrieve and display tomorrow's AQI data
        String aqiTomorrowData = DataStockage.getInstance().getAqiTomorrowData();
        tomorrowAQI.setText(getAqiLabel(aqiTomorrowData));

        // Set up buttons to open other activities
        Button buttonDataComparisonActivity = findViewById(R.id.buttonDataComparisonActivity);
        buttonDataComparisonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataComparisonActivity();
            }
        });

        Button buttonSensorsActivity = findViewById(R.id.buttonSensorsActivity);
        buttonSensorsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSensorsActivity();
            }
        });
    }


    // Method to get AQI label based on the AQI value
    private String getAqiLabel(String aqiValue) {
        Log.i("AqiValue", "Received value: " + aqiValue);
        if ("1".equals(aqiValue)) {
            return "Good";
        } else if ("2".equals(aqiValue)) {
            return "Fair";
        } else if ("3".equals(aqiValue)) {
            return "Moderate";
        } else if ("4".equals(aqiValue)) {
            return "Poor";
        } else {
            return "Very Poor";
        }
    }

    // Method to open DataComparisonActivity
    private void openDataComparisonActivity() {
        Intent intent = new Intent(this, ComparisonActivity.class);
        startActivity(intent);
    }

    // Method to open SensorsActivity
    private void openSensorsActivity() {
        Intent intent = new Intent(this, SensorsActivity.class);
        startActivity(intent);
    }
}
