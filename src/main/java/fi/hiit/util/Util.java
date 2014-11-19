package fi.hiit.util;

import java.util.Random;

public class Util {
    private static Random sRandom;

    private Util() { }

    // Fisher–Yates shuffle
    public static void shuffleIntArrayInPlace(int[] array) {
        if (sRandom == null) {
            sRandom = new Random();
        }

        int count = array.length;
        int j, temp;
        for (int i = count; i>1; i--) {
            j = sRandom.nextInt(i);

            // Swap arrray[i-1] and array[j]
            temp = array[i-1];
            array[i-1] = array[j];
            array[j] = temp;
        }
    }

}
