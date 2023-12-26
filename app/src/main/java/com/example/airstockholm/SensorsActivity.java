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

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize sensor data list
        sensorDataList = new ArrayList<>();
        sensorDataList.add(new SensorsData("Attributes", "Current Data", "Safe Level"));

        // Populate sensor data list with actual data
        sensorDataList.add(new SensorsData("so2", DataStockage.getInstance().getSo2Data(), "[0; 20)"));
        sensorDataList.add(new SensorsData("no2", DataStockage.getInstance().getNo2Data(), "[0; 40)"));
        sensorDataList.add(new SensorsData("pm10", DataStockage.getInstance().getPm10Data(), "[0; 20)"));
        sensorDataList.add(new SensorsData("pm2.5", DataStockage.getInstance().getPm25Data(), "[0; 10)"));
        sensorDataList.add(new SensorsData("o3", DataStockage.getInstance().getO3Data(), "[0; 60)"));
        sensorDataList.add(new SensorsData("co", DataStockage.getInstance().getCoData(), "[0; 4400)"));

        // Initialize and set up RecyclerView adapter
        sensorsAdapter = new SensorsAdapter(sensorDataList);
        recyclerView.setAdapter(sensorsAdapter);
    }
}
