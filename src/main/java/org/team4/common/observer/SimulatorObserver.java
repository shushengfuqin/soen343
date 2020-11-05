package org.team4.common.observer;

import org.team4.common.Settings;
import org.team4.common.logger.Logger;
import org.team4.house.House;
import java.util.Observable;
import java.util.Observer;

@Deprecated
public class SimulatorObserver implements Observer {

    @Deprecated
    public SimulatorObserver(Observable o) {
        o.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg)
    {
        if(Settings.awayMode) {
            performAwayModeChecks();
            return;
        }

        if(Settings.lightAutoMode) {
            performAutoModeCheck();
        }
    }

    public void performAwayModeChecks() {
        if(House.userInHouse()) {
            Logger.warning("Movement detected inside the house.");
            Settings.startTimeBeforeAlerting = true;
        }
    }

    public void performAutoModeCheck() {
        House.turnOnAllLightsWithUsers();

    }

}
