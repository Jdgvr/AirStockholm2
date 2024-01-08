    package com.example.airstockholm;


    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.TextView;

    import androidx.appcompat.app.AppCompatActivity;

    import org.json.JSONException;
    import org.json.JSONObject;

    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.Locale;

    public class MainActivity extends AppCompatActivity {

        private TextView temperature, daytime, aqi, todayDate;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.act_main);

            todayDate = findViewById(R.id.tvLocationAndDate);
            temperature = findViewById(R.id.tvTemperature);
            aqi = findViewById(R.id.tvAirQuality);
            daytime = findViewById(R.id.tvGoodMorning);

            aqi.setText("Calculating AQI...");

        }

        @Override
        protected void onResume() {
            super.onResume();

            // updates the current date
            Date date = new Date();
            todayDate.setText(CommonTools.updateUI_Date());
            updateUI_Daytime(date);

            // API call. We use a thread to handle connection and UI operations separately
            new Thread(new Runnable() {

                @Override
                public void run() {
                    final HttpHandler httpHandler = new HttpHandler();
                    final String response;
                    response = httpHandler.callApi("http://192.168.64.3:5001/pred_aqi");
                    // note that the app tries to connect to our server on the virtual machine.
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                if (response != null) {
                                    // sets the sensor values to be ready for the other activities
                                    DataStockage.getInstance().setSensors_values(new JSONObject(response));
                                }
                                else{
                                    DataStockage.getInstance().setSensors_values(new JSONObject("{'AQI' : 0.0, 'CO' : 0.0, " +
                                            "'SO2' : 0.0, 'NO' : 0.0, 'NO2' : 0.0, 'PM10' : 0.0, 'PM25' : 0.0, 'O3' : 0.0, " +
                                            "'NH3' : 0.0, 'T' : 0.0, 'AQI_P' : 0.0}")); // default values if the server is offline
                                    daytime.setText("The server is offline.");
                                }
                                updateUI_AQI_P();
                                temperature.setText(CommonTools.updateUI_Temperature());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }).start();

        }

        // handles the AQI prediction
        private void updateUI_AQI_P() {
            int aqi_int = DataStockage.getInstance().getAQI_P();
            String aqi_string = CommonTools.getAQIString(aqi_int);
            aqi.setText("Tomorrow, Air Quality will be " + aqi_string);

            checkBackground(aqi_int);
        }

        // handles the daytime (morning, afternoon, evening, night)
        private void updateUI_Daytime(Date date){
            SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.getDefault());
            int hour = Integer.parseInt(sdf.format(date));
            String daytimeString;
            if (hour >= 6 && hour < 12) {
                daytimeString = "Morning!";
            } else if (hour >= 12 && hour < 18) {
                daytimeString = "Afternoon!";
            } else if (hour >= 18 && hour < 22) {
                daytimeString = "Evening!";
            } else {
                daytimeString = "Night!";
            }
            daytime.setText("Good " + daytimeString);
        }

        // it changes the background and communicates it to the other activities through SharedPreferences strings
        private void checkBackground(int aqi){

            SharedPreferences sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
            SharedPreferences.Editor bg_editor = sharedPreferences.edit();

            switch(aqi){
                case 1:
                    bg_editor.putString("background", "bg1");
                    bg_editor.apply();
                    findViewById(R.id.main_act).setBackgroundResource(R.drawable.background_1);
                    break;
                case 2:
                    bg_editor.putString("background", "bg2");
                    bg_editor.apply();
                    findViewById(R.id.main_act).setBackgroundResource(R.drawable.background_2);
                    break;
                case 3:
                    bg_editor.putString("background", "bg3");
                    bg_editor.apply();
                    findViewById(R.id.main_act).setBackgroundResource(R.drawable.background_3);
                    break;
                case 4:
                    bg_editor.putString("background", "bg4");
                    bg_editor.apply();
                    findViewById(R.id.main_act).setBackgroundResource(R.drawable.background_4);
                    break;
                case 5:
                    bg_editor.putString("background", "bg5");
                    bg_editor.apply();
                    findViewById(R.id.main_act).setBackgroundResource(R.drawable.background_5);
                    break;
                default:
                    bg_editor.putString("background", "no_data");
                    bg_editor.apply();
                    findViewById(R.id.main_act).setBackgroundResource(R.drawable.background_1);
                    break;
            }
        }

        // handle the clickable elements to move through the activities
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