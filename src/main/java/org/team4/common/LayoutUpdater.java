package org.team4.common;

import org.team4.App;
import org.team4.dashboard.DashboardController;

public class LayoutUpdater extends Thread{
    public boolean stop = false;

    public void run(){
        DashboardController dashboardController = App.fxmlLoader.getController();
        while(!stop){
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dashboardController.drawHouseLayout();
        }
    }
}
