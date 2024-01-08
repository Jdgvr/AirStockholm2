package com.example.airstockholm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ComparisonActivity extends AppCompatActivity {
    private TextView todayDate;
    private TextView temperature;
    private TextView green_box_title, yellow_box_text, green_box_text;
    private FrameLayout yellow_box_frame, green_box_frame;
    private TextView aqi_insight, aqi_text, aqi_title;
    private TextView temperature_insight, temperature_text, temperature_title;
    private TextView ozone_insight, ozone_text, ozone_title;
    private TextView pm25_insight, pm25_text, pm25_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_comparison);

        todayDate = findViewById(R.id.tvLocationAndDate);
        temperature = findViewById(R.id.tvTemperature);

        green_box_title = findViewById(R.id.tvGoodAQITitle);
        green_box_text = findViewById(R.id.tvGoodAQIDetail);
        yellow_box_text = findViewById(R.id.tvMaskAdviceDetail);
        yellow_box_frame = findViewById(R.id.frameMaskAdvice);
        green_box_frame = findViewById(R.id.frameGoodAQI);

        aqi_insight = findViewById(R.id.aqiInsight);
        aqi_text = findViewById(R.id.aqiText);
        aqi_title = findViewById(R.id.aqiTitle);

        temperature_insight = findViewById(R.id.tempInsight);
        temperature_text = findViewById(R.id.tempText);
        temperature_title = findViewById(R.id.tempTitle);

        ozone_insight = findViewById(R.id.ozoneInsight);
        ozone_text = findViewById(R.id.ozoneText);
        ozone_title = findViewById(R.id.ozoneTitle);

        pm25_insight = findViewById(R.id.pm25Insight);
        pm25_text = findViewById(R.id.pm25Text);
        pm25_title = findViewById(R.id.pm25Title);

        // to handle the change of background (that comes from MainActivity)
        SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        String background = sharedPreferences.getString("background", "bg1");

        switch(background){
            case "bg2":
                findViewById(R.id.comp_act).setBackgroundResource(R.drawable.background_2);
                break;
            case "bg3":
                findViewById(R.id.comp_act).setBackgroundResource(R.drawable.background_3);
                break;
            case "bg4":
                findViewById(R.id.comp_act).setBackgroundResource(R.drawable.background_4);
                break;
            case "bg5":
                findViewById(R.id.comp_act).setBackgroundResource(R.drawable.background_5);
                break;
            default:
                findViewById(R.id.comp_act).setBackgroundResource(R.drawable.background_1);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        todayDate.setText(CommonTools.updateUI_Date());
        temperature.setText(CommonTools.updateUI_Temperature());

        updateUI_MaskAdviceBox(DataStockage.getInstance().getAQI());
        updateUI_AQIComp(DataStockage.getInstance().getAQI());
        updateUI_TemperatureComp(DataStockage.getInstance().getTemperature());
        updateUI_OzoneComp(DataStockage.getInstance().getFeatureValue("O3"));
        updateUI_PM25Comp(DataStockage.getInstance().getFeatureValue("PM25"));

    }

    // to show the correct advice in the box
    private void updateUI_MaskAdviceBox(int current_aqi) {
        if (current_aqi >= 4) {
            yellow_box_frame.setVisibility(View.VISIBLE);
            green_box_frame.setVisibility(View.INVISIBLE);
            if (current_aqi == 4) {
                yellow_box_text.setText("Today's AQI is Poor: you should consider wearing a mask if you go out.");
            } else {
                yellow_box_text.setText("Today's AQI is Very Poor: if you go out, a mask is strongly advised.");
            }
        } else {
            yellow_box_frame.setVisibility(View.INVISIBLE);
            green_box_frame.setVisibility(View.VISIBLE);
            green_box_title.setText("Today's AQI is " + CommonTools.getAQIString(current_aqi));
            if(current_aqi == 0) {
                green_box_text.setText("The server is offline. We can't display suggestions about AQI.");
            }
        }
    }

    // to show today's AQI in the related card
    private void updateUI_AQIComp(int current_aqi) {
        aqi_insight.setText("Today, Air Quality is " + CommonTools.getAQIString(current_aqi) +
                " Usually, Air Quality in Stockholm is Good.");
    }

    // to show the temperature data in the related card
    private void updateUI_TemperatureComp(int temperature) {
        Calendar calendar = Calendar.getInstance();
        int current_month = calendar.get(Calendar.MONTH);
        int[] avgMonthlyTemperature = {-2, -2, 1, 5, 11, 15, 18, 16, 12, 7, 3, -1}; // monthly average temperatures in Stockholm (in Celsius)

        int temperatureDifference = temperature - avgMonthlyTemperature[current_month];

        if (temperature > avgMonthlyTemperature[current_month]) {
            temperature_insight.setText("There are " + temperature + "°C outside. It is " + Math.abs(temperatureDifference) +
                    "°C above the monthly average (" + avgMonthlyTemperature[current_month] + "°C).");
        } else if (temperature < avgMonthlyTemperature[current_month]) {
            temperature_insight.setText("There are " + temperature + "°C outside. It is " + Math.abs(temperatureDifference) +
                    "°C below the monthly average (" + avgMonthlyTemperature[current_month] + "°C).");
        } else {
            temperature_insight.setText("There are " + temperature + "°C outside, in line with the monthly average.");
        }
    }

    // to handle the Ozone comparison
    private void updateUI_OzoneComp(double ozone) {
        String ozoneLevelText;
        if (ozone > 140) {
            ozoneLevelText = "Today, ozone level is high (" + ozone + " μg/m3).";
        } else if (ozone >= 60) {
            ozoneLevelText = "Today, ozone level is moderate (" + ozone + " μg/m3).";
        } else {
            ozoneLevelText = "Today, ozone level is low (" + ozone + " μg/m3).";
        }
        ozone_insight.setText(ozoneLevelText);
    }

    // to handle PM2.5 related card
    private void updateUI_PM25Comp(double pm25) {
        double cigarettesPerUnitPM25 = 1.0 / 22.0;
        double equivalentCigarettes = pm25 * cigarettesPerUnitPM25;
        equivalentCigarettes = Math.round(equivalentCigarettes * 10.0) / 10.0; // computes roughly how many cigarettes correspond to PM2.5 levels in a day

        pm25_insight.setText("Today, if you spend the whole day outside, it will be like smoking " + equivalentCigarettes +
                " cigarettes (PM2.5 levels: " + pm25 + " μg/m3)");
    }


    // the methods below handle the clickable elements (to show/hide text, to move through the activities)
    public void onAQICompClick(View view) {
        if (aqi_insight.getVisibility() == View.GONE) {
            aqi_insight.setVisibility(View.VISIBLE);
            aqi_title.setVisibility(View.GONE);
            aqi_text.setVisibility(View.GONE);
        } else {
            aqi_insight.setVisibility(View.GONE);
            aqi_title.setVisibility(View.VISIBLE);
            aqi_text.setVisibility(View.VISIBLE);
        }
    }

    public void onTemperatureCompClick(View view) {
        if (temperature_insight.getVisibility() == View.GONE) {
            temperature_insight.setVisibility(View.VISIBLE);
            temperature_title.setVisibility(View.GONE);
            temperature_text.setVisibility(View.GONE);
        } else {
            temperature_insight.setVisibility(View.GONE);
            temperature_title.setVisibility(View.VISIBLE);
            temperature_text.setVisibility(View.VISIBLE);
        }
    }

    public void onOzoneCompClick(View view) {
        if (ozone_insight.getVisibility() == View.GONE) {
            ozone_insight.setVisibility(View.VISIBLE);
            ozone_title.setVisibility(View.GONE);
            ozone_text.setVisibility(View.GONE);
        } else {
            ozone_insight.setVisibility(View.GONE);
            ozone_title.setVisibility(View.VISIBLE);
            ozone_text.setVisibility(View.VISIBLE);
        }
    }

    public void onPM25CompClick(View view) {
        if (pm25_insight.getVisibility() == View.GONE) {
            pm25_insight.setVisibility(View.VISIBLE);
            pm25_title.setVisibility(View.GONE);
            pm25_text.setVisibility(View.GONE);
        } else {
            pm25_insight.setVisibility(View.GONE);
            pm25_title.setVisibility(View.VISIBLE);
            pm25_text.setVisibility(View.VISIBLE);
        }
    }

    public void onComparisonClick(View view) {
        Intent intent = new Intent(this, ComparisonActivity.class);
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