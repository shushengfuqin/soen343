package org.team4.common;

import org.team4.App;
import org.team4.DashboardController;

import java.util.Calendar;
import java.util.Date;

public class SimulationTime extends Thread{
    private Date date = new Date();
    private boolean setting = false;
    public boolean stop = false;
    public int multiplier = 1;

    public void run(){
        Date dt;
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(!stop){
            try {
                sleep(1000 / multiplier);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(this.date);
            c.add(Calendar.SECOND, 1);
            dt = c.getTime();
            setDateTime(dt);
            DashboardController dashboardController = App.fxmlLoader.getController();
            dashboardController.updateTime(getTime());
        }
    }

    /**
     *
     * @return the current date/time
     */
    public synchronized Date getTime(){
        while(setting){
            try {
                wait();
            } catch (InterruptedException e) {  }
        }

        return this.date;
    }

    /**
     * Set the new date/time
     * @param dt
     */
    public synchronized void setDateTime(Date dt){
        setting = true;
        this.date = dt;
        setting = false;
        notifyAll();
    }
}