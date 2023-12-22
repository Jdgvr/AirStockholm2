    package com.example.airstockholm;

    import androidx.core.content.ContextCompat;

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

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            title = findViewById(R.id.textViewTitle);
            todayDate = findViewById(R.id.textDate);
            temperature = findViewById(R.id.textViewTemperatureLabel);
            temperatureLevel = findViewById(R.id.textViewTemperature);
            aqi = findViewById(R.id.textViewAqiLabel);
            aqiResult = findViewById(R.id.textViewAqi);

            //Display date
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH);
            String formattedDate = dateFormat.format(currentDate);
            todayDate.setText(formattedDate);

            String temperatureData = DataStockage.getInstance().getTemperatureData();
            temperatureLevel.setText(String.valueOf(temperatureData));

            String aqiData = DataStockage.getInstance().getAqiData();
            aqiResult.setText(String.valueOf(aqiData));
            new Http(temperatureLevel, aqiResult, null, null, null, null, null, null, null, null).execute();

            //Open new activity -> DataComparison
            Button buttonDataComparisonActivity = findViewById(R.id.buttonDataComparisonActivity);
            buttonDataComparisonActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDataComparisonActivity();
                }
            });

            //Open new activity -> SensorsActivity
            Button buttonSensorsActivity = findViewById(R.id.buttonSensorsActivity);
            buttonSensorsActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openSensorsActivity();
                }
            });
        }

        private void setAqiColor(TextView aqiResultView, String aqiResultText) {

            aqiResultView.setText(aqiResultText);

            if ("Good".equals(aqiResultText)) {
                aqiResultView.setTextColor(ContextCompat.getColor(this, R.color.green));
            } else if ("Fair".equals(aqiResultText)) {
                aqiResultView.setTextColor(ContextCompat.getColor(this, R.color.yellow));
            } else if ("Moderate".equals(aqiResultText)) {
                aqiResultView.setTextColor(ContextCompat.getColor(this, R.color.orange));
            } else if ("Poor".equals(aqiResultText)) {
                aqiResultView.setTextColor(ContextCompat.getColor(this, R.color.darkOrange));
            } else if ("Very Poor".equals(aqiResultText)) {
                aqiResultView.setTextColor(ContextCompat.getColor(this, R.color.red));
            }
        }

        private void openDataComparisonActivity() {
            Intent intent = new Intent(this, ComparisonActivity.class);
            startActivity(intent);
        }
        private void openSensorsActivity() {
            Intent intent = new Intent(this, SensorsActivity.class);
            startActivity(intent);
        }
    }