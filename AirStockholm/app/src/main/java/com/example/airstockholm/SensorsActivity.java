package com.example.airstockholm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SensorsActivity extends AppCompatActivity {

    TextView value_header;
    private TextView todayDate, temperature;
    private TextView so2_range, so2_value, so2_insight;
    private TextView co_range, co_value, co_insight;
    private TextView nh3_range, nh3_value, nh3_insight;
    private TextView pm10_range, pm10_value, pm10_insight;
    private TextView pm25_range, pm25_value, pm25_insight;
    private TextView no2_range, no2_value, no2_insight;
    private TextView o3_range, o3_value, o3_insight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_sensors);

        todayDate = findViewById(R.id.tvLocationAndDate);
        temperature = findViewById(R.id.tvTemperature);
        value_header = findViewById(R.id.tvValue);

        so2_range = findViewById(R.id.tvSO2Range);
        so2_value = findViewById(R.id.tvSO2Value);

        co_range = findViewById(R.id.tvCORange);
        co_value = findViewById(R.id.tvCOValue);

        nh3_range = findViewById(R.id.tvNH3Range);
        nh3_value = findViewById(R.id.tvNH3Value);

        pm10_range = findViewById(R.id.tvPM10Range);
        pm10_value = findViewById(R.id.tvPM10Value);

        pm25_range = findViewById(R.id.tvPM25Range);
        pm25_value = findViewById(R.id.tvPM25Value);

        no2_range = findViewById(R.id.tvNO2Range);
        no2_value = findViewById(R.id.tvNO2Value);

        o3_range = findViewById(R.id.tvO3Range);
        o3_value = findViewById(R.id.tvO3Value);

        o3_insight = findViewById(R.id.tvO3Insight);
        co_insight = findViewById(R.id.tvCOInsight);
        nh3_insight = findViewById(R.id.tvNH3Insight);
        pm10_insight = findViewById(R.id.tvPM10Insight);
        pm25_insight = findViewById(R.id.tvPM25Insight);
        no2_insight = findViewById(R.id.tvNO2Insight);
        so2_insight = findViewById(R.id.tvSO2Insight);

        // changes background based on the Main Activity

        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        String background = sharedPreferences.getString("background", "default_background");

        switch(background){
            case "bg2":
                findViewById(R.id.sensors_act).setBackgroundResource(R.drawable.background_2);
                break;
            case "bg3":
                findViewById(R.id.sensors_act).setBackgroundResource(R.drawable.background_3);
                break;
            case "bg4":
                findViewById(R.id.sensors_act).setBackgroundResource(R.drawable.background_4);
                break;
            case "bg5":
                findViewById(R.id.sensors_act).setBackgroundResource(R.drawable.background_5);
                break;
            default:
                findViewById(R.id.sensors_act).setBackgroundResource(R.drawable.background_1);
                break;
        }

        if(background.equals("no_data")){
            value_header.setText("No data.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        todayDate.setText(CommonTools.updateUI_Date());
        temperature.setText(CommonTools.updateUI_Temperature());

        // sets the values for the sensors
        pm25_value.setText("" + DataStockage.getInstance().getFeatureValue("PM25"));
        co_value.setText("" + DataStockage.getInstance().getFeatureValue("CO"));
        nh3_value.setText("" + DataStockage.getInstance().getFeatureValue("NH3"));
        pm10_value.setText("" + DataStockage.getInstance().getFeatureValue("PM10"));
        no2_value.setText("" + DataStockage.getInstance().getFeatureValue("NO2"));
        so2_value.setText("" + DataStockage.getInstance().getFeatureValue("SO2"));
        o3_value.setText("" + DataStockage.getInstance().getFeatureValue("O3"));


    }

    // handle the clickable elements (to show/hide the text, to move through the activities)
    public void onO3Click(View view) {
        if (o3_insight.getVisibility() == View.GONE) {
            o3_insight.setVisibility(View.VISIBLE);
            o3_value.setVisibility(View.GONE);
            o3_range.setVisibility(View.GONE);
        } else {
            o3_insight.setVisibility(View.GONE);
            o3_value.setVisibility(View.VISIBLE);
            o3_range.setVisibility(View.VISIBLE);
        }
    }

    public void onCOClick(View view) {
        if (co_insight.getVisibility() == View.GONE) {
            co_insight.setVisibility(View.VISIBLE);
            co_value.setVisibility(View.GONE);
            co_range.setVisibility(View.GONE);
        } else {
            co_insight.setVisibility(View.GONE);
            co_value.setVisibility(View.VISIBLE);
            co_range.setVisibility(View.VISIBLE);
        }
    }

    public void onNH3Click(View view) {
        if (nh3_insight.getVisibility() == View.GONE) {
            nh3_insight.setVisibility(View.VISIBLE);
            nh3_value.setVisibility(View.GONE);
            nh3_range.setVisibility(View.GONE);
        } else {
            nh3_insight.setVisibility(View.GONE);
            nh3_value.setVisibility(View.VISIBLE);
            nh3_range.setVisibility(View.VISIBLE);
        }
    }

    public void onPM10Click(View view) {
        if (pm10_insight.getVisibility() == View.GONE) {
            pm10_insight.setVisibility(View.VISIBLE);
            pm10_value.setVisibility(View.GONE);
            pm10_range.setVisibility(View.GONE);
        } else {
            pm10_insight.setVisibility(View.GONE);
            pm10_value.setVisibility(View.VISIBLE);
            pm10_range.setVisibility(View.VISIBLE);
        }
    }

    public void onPM25Click(View view) {
        if (pm25_insight.getVisibility() == View.GONE) {
            pm25_insight.setVisibility(View.VISIBLE);
            pm25_value.setVisibility(View.GONE);
            pm25_range.setVisibility(View.GONE);
        } else {
            pm25_insight.setVisibility(View.GONE);
            pm25_value.setVisibility(View.VISIBLE);
            pm25_range.setVisibility(View.VISIBLE);
        }
    }

    public void onNO2Click(View view) {
        if (no2_insight.getVisibility() == View.GONE) {
            no2_insight.setVisibility(View.VISIBLE);
            no2_value.setVisibility(View.GONE);
            no2_range.setVisibility(View.GONE);
        } else {
            no2_insight.setVisibility(View.GONE);
            no2_value.setVisibility(View.VISIBLE);
            no2_range.setVisibility(View.VISIBLE);
        }
    }

    public void onSO2Click(View view) {
        if (so2_insight.getVisibility() == View.GONE) {
            so2_insight.setVisibility(View.VISIBLE);
            so2_value.setVisibility(View.GONE);
            so2_range.setVisibility(View.GONE);
        } else {
            so2_insight.setVisibility(View.GONE);
            so2_value.setVisibility(View.VISIBLE);
            so2_range.setVisibility(View.VISIBLE);
        }
    }

    public void onComparisonClick(View view) {
        Intent intent = new Intent(this, com.example.airstockholm.ComparisonActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onTableClick(View view) {
        Intent intent = new Intent(this, SensorsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onHomeClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // to not have a new Main instance
        startActivity(intent);
    }
}