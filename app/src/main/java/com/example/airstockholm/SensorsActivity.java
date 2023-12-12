package com.example.airstockholm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SensorsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SensorsAdapter sensorsAdapter;
    private List<SensorsData> sensorDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sensorDataList = new ArrayList<>();
        sensorDataList.add(new SensorsData("Attributes", 1, "Safe Level"));
        sensorDataList.add(new SensorsData("so2", DataTemperature.generateRandomTemperature(), "[0; 20)"));
        sensorDataList.add(new SensorsData("no2", DataTemperature.generateRandomTemperature(), "[0; 40)"));
        sensorDataList.add(new SensorsData("pm10", DataTemperature.generateRandomTemperature(), "[0; 20)"));
        sensorDataList.add(new SensorsData("pm2.5", DataPm25.generateRandomPm25(), "[0; 10)"));
        sensorDataList.add(new SensorsData("o3", DataOzone.generateRandomOzone(), "[0; 60)"));
        sensorDataList.add(new SensorsData("co", DataTemperature.generateRandomTemperature(), "[0; 4400)"));

        sensorsAdapter = new SensorsAdapter(sensorDataList);
        recyclerView.setAdapter(sensorsAdapter);
    }
}