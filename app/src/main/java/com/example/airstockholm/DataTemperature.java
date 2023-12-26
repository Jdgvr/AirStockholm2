package com.example.airstockholm;

public class DataTemperature {

    private static final int[] monthTemperature = {-2, -2, 1, 5, 11, 15, 18, 16, 12, 7, 3, -1};

    public static int getAverageTemperature(int month) {
            return monthTemperature[month];
    }
}

