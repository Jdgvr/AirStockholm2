package com.example.airstockholm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHandler {

    // to handle the communication between the server (through API and the application)
    public String callApi(String url_string) {
        String response = null;
        try {
            URL url = new URL(url_string);
            // opens the connection between the app and the server
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // gets the data from the server (JSON formatted string with AQI, AQI prediction, temperature and sensors data)
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;

            // builds the response to be effectively used
            StringBuilder responseStrBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                responseStrBuilder.append(inputLine);
            }
            in.close();
            response = responseStrBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}



