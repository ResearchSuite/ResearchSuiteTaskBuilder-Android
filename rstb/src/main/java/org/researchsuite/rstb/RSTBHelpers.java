package org.researchsuite.rstb;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jameskizer on 2/2/17.
 */
public class RSTBHelpers {

    static Random rnd = new Random();

    public static <T> void shuffle(T[] ar) {
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            T a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static <T> List<T> shuffled(List<T> list) {

        ArrayList<T> ar = new ArrayList(list);

        for (int i = ar.size() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            T a = ar.get(i);
            ar.set(i, ar.get(index));
            ar.set(index, a);
        }

        return ar;
    }

    public static JsonArray shuffled(JsonArray jsonArray) {

        JsonArray shuffledArray = new JsonArray();
        shuffledArray.addAll(jsonArray);

        for (int i = shuffledArray.size() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            JsonElement a = shuffledArray.get(index);
            shuffledArray.set(index, shuffledArray.get(i));
            shuffledArray.set(i, a);
        }

        return shuffledArray;
    }

    public static JsonElement randomElement(JsonArray jsonArray) {
        int index = rnd.nextInt(jsonArray.size());
        return jsonArray.get(index);
    }

    static public <T> T coinFlip(T obj1, T obj2, double bias) {

        double realBias = Math.max( Math.min(bias, 1.0), 0.0 );
        double flip = rnd.nextDouble();
        if (flip < realBias) {
            return obj1;
        }
        else {
            return obj2;
        }

    }

}