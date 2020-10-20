package org.team4.shsParameters;

import org.team4.App;
import org.team4.DashboardController;

import java.util.Calendar;
import java.util.Date;

public class SimulationTime extends Thread{
    private Date date = new Date(120, 10, 16, 20, 22, 35);
    private boolean setting = false;
    public boolean stop = false;

    public void run(){
        Date dt;
        while(!stop){
            try {
                sleep(1000);
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