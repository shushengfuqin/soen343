package org.team4.common;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Calendar;
import java.util.Date;

public class SimulationTime {
    private final Calendar date;
    private double multiplier;
    private Timeline clock;

    public SimulationTime() {
        date = Calendar.getInstance();
        multiplier = 1;
        setClockSpeed(multiplier);
    }

    public void startTime() {
        clock.play();
    }

    public void stopTime() {
        clock.pause();
    }

    public void setDateTime(Date date) {
        this.date.set(Calendar.DAY_OF_MONTH, date.getDate());
        this.date.set(Calendar.MONTH, date.getMonth());
        this.date.set(Calendar.YEAR, date.getYear());
        this.date.set(Calendar.HOUR, date.getHours());
        this.date.set(Calendar.MINUTE, date.getMinutes());
        this.date.set(Calendar.SECOND, date.getSeconds());
    }

    public Calendar getDate() {
        return date;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
        changeClockSpeed(multiplier);
    }

    public double getMultiplier() {
        return multiplier;
    }

    private void setClockSpeed(double multiplier) {
        if (clock != null) {
            clock.stop();
        }
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            date.add(Calendar.SECOND, 1);
        }),
                new KeyFrame(Duration.seconds(1/multiplier))
        );
        clock.setCycleCount(Animation.INDEFINITE);
    }

    private void changeClockSpeed(double multiplier) {
        setClockSpeed(multiplier);
        startTime();
    }
}
