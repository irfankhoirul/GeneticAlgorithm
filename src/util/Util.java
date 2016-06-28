package util;

import java.util.Random;

/**
 * Created by Irfan Khoirul on 6/28/2016.
 */
public class Util {
    public static int randInt(int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
