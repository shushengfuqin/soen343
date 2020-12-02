package org.team4.common.observer;

import org.team4.common.Settings;
import org.team4.common.logger.Logger;
import org.team4.house.services.LightService;
import org.team4.user.UserService;

import java.util.Observable;
import java.util.Observer;

@Deprecated
public class SimulatorObserver implements Observer {

    private LightService lightService;
    private UserService userService;

    @Deprecated
    public SimulatorObserver(Observable o) {
        o.addObserver(this);
        lightService = new LightService();
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

    /**
     * Performs the away mode actions
     */
    public void performAwayModeChecks() {
        if(userService.userInHouse()) {
            Logger.warning("Movement detected inside the house.");
            Settings.startTimeBeforeAlerting = true;
        }
    }

    /**
     * Performs the auto mode action
     */
    public void performAutoModeCheck() {
        lightService.turnOnAllLightsWithUsers();

    }

}
