package org.team4.common.observer;

import java.util.Observer;

public class ObservableHandler {

    @Deprecated
    UserMovement userMovement;
    @Deprecated
    Observer simulatorObserver;

    @Deprecated
    public ObservableHandler() {
        userMovement = new UserMovement();
        simulatorObserver = new SimulatorObserver(userMovement);
    }

    public void notifyUserMovement() {
        userMovement.userMoved();
    }
}
