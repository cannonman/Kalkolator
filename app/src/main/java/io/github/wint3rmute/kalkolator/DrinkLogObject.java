package io.github.wint3rmute.kalkolator;

import java.util.Date;

/**
 * Created by mateu on 16/01/2018.
 */

public class DrinkLogObject {

    private String date;
    private float liters;
    private float percentage;

    public DrinkLogObject(String date, float liters, float percentage) {
        this.date = date;
        this.liters = liters;
        this.percentage = percentage;
    }

    public String getDate() {
        return date;
    }

    public float getLiters() {
        return liters;
    }

    public float getPercentage() {
        return percentage;
    }
}
