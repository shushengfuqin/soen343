package org.team4.common.observer;

import org.team4.common.Settings;
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
        if(!Settings.lightAutoMode) return;
        House.turnOnAllLightsWithUsers();
    }

}
