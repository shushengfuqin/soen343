package org.team4.common;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Calendar;

public class SimulationTime {
    private Calendar date;
    private double multiplier;
    private Timeline clock;

    public SimulationTime() {
        date = Calendar.getInstance();
        multiplier = 1;
        initializeClock();
    }

    public void startTime() {
        clock.play();
    }

    public void stopTime() {
        clock.pause();
    }

    public void setDateTime(Calendar calendar) {
        this.date = calendar;
    }

    public Calendar getDate() {
        return date;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
        changeClockSpeed();
    }

    public double getMultiplier() {
        return multiplier;
    }

    private void initializeClock() {
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> date.add(Calendar.SECOND, 1)),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
    }

    private void changeClockSpeed() {
        clock.setRate(multiplier);
    }
}
