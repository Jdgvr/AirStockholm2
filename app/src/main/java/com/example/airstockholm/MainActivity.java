package com.example.airstockholm;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView title;
    private TextView temperature;
    private TextView temperatureLevel;
    private TextView aqi;
    private TextView aqiResult;
    private Button buttonRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.textViewTitle);
        temperature = findViewById(R.id.textViewTemperatureLabel);
        temperatureLevel = findViewById(R.id.textViewTemperature);
        aqi = findViewById(R.id.textViewAqiLabel);
        aqiResult = findViewById(R.id.textViewAqi);
        buttonRefresh = findViewById(R.id.buttonRefresh);

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateRandomData();
            }
        });
    }
    private void generateRandomData() {
        int temp = new Random().nextInt(11) - 10;
        temperatureLevel.setText(String.valueOf(temp));

        String[] aqiIndex = {"Good", "Fair"};
        String aqiResult = aqiIndex[new Random().nextInt(aqiIndex.length)];
        this.aqiResult.setText(aqiResult);

        if (aqiResult.equals("Good")) {
            this.aqiResult.setTextColor(Color.GREEN);
        } else {
            this.aqiResult.setTextColor(Color.YELLOW);
        }
    }
}