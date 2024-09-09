package com.j0schi.server.vot.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class RandomUtil {

    public int getRandomInt(){
        Random random = new Random();
        return random.nextInt();
    }

    public int getRandomInt(int max){
        Random random = new Random();
        return random.nextInt(max);
    }

    public int getThreadLocalRandomInt(){
        return ThreadLocalRandom.current().nextInt();
    }

    public int getThreadLocalRandomInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static String getThreadLocalRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }

}
