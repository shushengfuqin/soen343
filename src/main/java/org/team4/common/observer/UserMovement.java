package org.team4.common.observer;

import java.util.Observable;

@Deprecated
public class UserMovement extends Observable {

    /**
     * Updates when user moves
     */
    public void userMoved() {
        setChanged();
        notifyObservers();
    }
}

