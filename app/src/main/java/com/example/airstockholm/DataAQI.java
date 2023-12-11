package com.example.airstockholm;

import java.util.Random;

public class DataAQI {
    public static int generateRandomAQI() {
        return new Random().nextInt(5) +1;
    }

}
