package com.example.airstockholm;

import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Http extends AsyncTask<Void, Void, String> {

    private TextView temperatureLevel;
    private TextView aqiLabel;
    private TextView coLabel;
    private TextView noLabel;
    private TextView no2Label;
    private TextView o3Label;
    private TextView so2Label;
    private TextView pm25Label;
    private TextView pm10Label;
    private TextView nh3Label;

    public Http(TextView temperatureLevel,
                TextView aqiLabel,
                TextView coLabel,
                TextView noLabel,
                TextView no2Label,
                TextView o3Label,
                TextView so2Label,
                TextView pm25Label,
                TextView pm10Label,
                TextView nh3Label) {
        this.temperatureLevel = temperatureLevel;
        this.aqiLabel = aqiLabel;
        this.coLabel = coLabel;
        this.noLabel = noLabel;
        this.no2Label = no2Label;
        this.o3Label = o3Label;
        this.so2Label = so2Label;
        this.pm25Label = pm25Label;
        this.pm10Label = pm10Label;
        this.nh3Label = nh3Label;
    }


    @Override
    protected String doInBackground(Void... voids) {
        String apiUrl = "http://192.168.1.57:8080/temperature";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Read the InputStream and process the data
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            DataStockage.getInstance().setTemperatureData(result.toString());
            DataStockage.getInstance().setAqiData(result.toString());
            DataStockage.getInstance().setCoData(result.toString());
            DataStockage.getInstance().setNoData(result.toString());
            DataStockage.getInstance().setNo2Data(result.toString());
            DataStockage.getInstance().setO3Data(result.toString());
            DataStockage.getInstance().setSo2Data(result.toString());
            DataStockage.getInstance().setPm25Data(result.toString());
            DataStockage.getInstance().setPm10Data(result.toString());
            DataStockage.getInstance().setNh3Data(result.toString());
            return result.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if ((result != null && temperatureLevel != null && aqiLabel != null)) {
            try {
                // Parse the JSON response
                JSONObject jsonObject = new JSONObject(result);

                double temperatureValue = jsonObject.getDouble("temperature");
                DataStockage.getInstance().setTemperatureData(String.valueOf(temperatureValue));

                int aqiValue = jsonObject.getInt("aqi");
                DataStockage.getInstance().setAqiData(String.valueOf(aqiValue));

                int coValue = jsonObject.getInt("co");
                DataStockage.getInstance().setCoData(String.valueOf(coValue));

                int noValue = jsonObject.getInt("no");
                DataStockage.getInstance().setNoData(String.valueOf(noValue));

                int no2Value = jsonObject.getInt("no2");
                DataStockage.getInstance().setNo2Data(String.valueOf(no2Value));

                int o3Value = jsonObject.getInt("o3");
                DataStockage.getInstance().setO3Data(String.valueOf(o3Value));

                int so2Value = jsonObject.getInt("so2");
                DataStockage.getInstance().setSo2Data(String.valueOf(so2Value));

                int pm25Value = jsonObject.getInt("pm25");
                DataStockage.getInstance().setPm25Data(String.valueOf(pm25Value));

                int pm10Value = jsonObject.getInt("pm10");
                DataStockage.getInstance().setPm10Data(String.valueOf(pm10Value));

                int nh3Value = jsonObject.getInt("nh3");
                DataStockage.getInstance().setNh3Data(String.valueOf(nh3Value));

                // Update the UI with the temperature value
                if (temperatureLevel != null) {
                    temperatureLevel.setText(DataStockage.getInstance().getTemperatureData());
                }
                if (aqiLabel != null) {
                    aqiLabel.setText(DataStockage.getInstance().getAqiData());
                }
                if (coLabel != null) {
                    coLabel.setText(DataStockage.getInstance().getCoData());
                }
                if (noLabel != null) {
                    noLabel.setText(DataStockage.getInstance().getNoData());
                }
                if (no2Label != null) {
                    no2Label.setText(DataStockage.getInstance().getNo2Data());
                }
                if (o3Label != null) {
                    o3Label.setText(DataStockage.getInstance().getO3Data());
                }
                if (so2Label != null) {
                    so2Label.setText(DataStockage.getInstance().getSo2Data());
                }
                if (pm25Label != null) {
                    pm25Label.setText(DataStockage.getInstance().getPm25Data());
                }
                if (pm10Label != null) {
                    pm10Label.setText(DataStockage.getInstance().getPm10Data());
                }
                if (nh3Label != null) {
                    nh3Label.setText(DataStockage.getInstance().getNh3Data());
                }
                Log.i("Http", "onPostExecute: " + result);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Http", "JSON parsing error: " + e.getMessage());
            }
        } else {
            // Handle the case where the data retrieval failed
            Log.e("Http", "Data retrieval failed");
        }
    }


}

