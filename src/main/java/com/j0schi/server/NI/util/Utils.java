package com.j0schi.server.NI.util;

import java.util.Random;

public class Utils {

    public static double randomFloat(double minInclusive, double maxInclusive, double precision) {
        int max = (int)(maxInclusive/precision);
        int min = (int)(minInclusive/precision);
        Random rand = new Random();
        int randomInt = rand.nextInt((max - min) + 1) + min;
        double randomNum = randomInt * precision;
        return randomNum;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
